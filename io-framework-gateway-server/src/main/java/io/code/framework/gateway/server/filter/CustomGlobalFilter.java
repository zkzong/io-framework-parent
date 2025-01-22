package io.code.framework.gateway.server.filter;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson2.JSON;
import io.code.framework.common.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author: zongz
 * @Date: 2025-01-21
 */
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final String BODY_ENCRYPT_HEADER = "X-ENCRYPTED";

    AES aes = SecureUtil.aes("keyskeyskeyskeys".getBytes());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求头中标识了该请求是加密过的
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
        String bodyEncryptHeader = httpHeaders.getFirst(BODY_ENCRYPT_HEADER);

        // 获取请求体
        ServerHttpRequest request = exchange.getRequest();
        // 获取响应体
        ServerHttpResponse response = exchange.getResponse();
        // 请求头
        HttpHeaders headers = request.getHeaders();
        // 请求方法
        HttpMethod method = request.getMethod();

        // 满足条件，进行过滤
        if ("yes".equalsIgnoreCase(bodyEncryptHeader)) {
            return DataBufferUtils.join(request.getBody())
                    .flatMap(dataBuffer -> {
                        try {
                            // 获取请求参数
                            String originalRequestBody = getOriginalRequestBody(dataBuffer);

                            // 解密请求参数
                            String decryptRequestBody = decryptRequest(originalRequestBody);

                            // 装饰新的请求体
                            ServerHttpRequestDecorator requestDecorator = serverHttpRequestDecorator(request, decryptRequestBody);

                            // 装饰新的响应体
                            ServerHttpResponseDecorator responseDecorator = serverHttpResponseDecorator(response);

                            // 使用新的请求和响应转发
                            ServerWebExchange serverWebExchange = exchange.mutate().request(requestDecorator).response(responseDecorator).build();

                            // 放行拦截
                            return chain.filter(serverWebExchange);
                        } catch (Exception e) {
                            log.error("密文过滤器加解密错误", e);
                            return Mono.empty();
                        } finally {
                            DataBufferUtils.release(dataBuffer);
                        }
                    });
        }

        // 处理未加密的请求
        return chain.filter(exchange);
    }

    private String decryptRequest(String originalRequestBody) {
        log.info("请求参数解密，原文：{}", originalRequestBody);
        String decrypted = aes.decryptStr(originalRequestBody);
        log.info("请求参数解密，明文：{}", decrypted);
        return decrypted;
    }

    private String encryptResponse(String originalResponseBody) {
        ApiResponse apiResponse = JSON.parseObject(originalResponseBody, ApiResponse.class);
        // 只对data字段进行加密处理
        Object data = apiResponse.getData();
        if (Objects.nonNull(data)) {
            apiResponse.setData(aes.encrypt(data.toString()));
        }
        log.info("响应结果加密，原文：{}", originalResponseBody);
        String result = JSON.toJSONString(apiResponse);
        log.info("响应结果加密，密文：{}", result);
        return result;
    }

    /**
     * 获取原始的请求参数
     *
     * @param dataBuffer 数据缓冲
     * @return 原始的请求参数
     */
    private String getOriginalRequestBody(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private ServerHttpRequestDecorator serverHttpRequestDecorator(ServerHttpRequest originalRequest, String decryptRequestBody) {
        return new ServerHttpRequestDecorator(originalRequest) {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                byte[] bytes = decryptRequestBody.getBytes(StandardCharsets.UTF_8);
                return Flux.just(new DefaultDataBufferFactory().wrap(bytes));
            }
        };
    }

    private ServerHttpResponseDecorator serverHttpResponseDecorator(ServerHttpResponse originalResponse) {
        DataBufferFactory dataBufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux<? extends DataBuffer> fluxBody) {
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] byteArray = new byte[join.readableByteCount()];
                        join.read(byteArray);
                        DataBufferUtils.release(join);

                        String originalResponseBody = new String(byteArray, StandardCharsets.UTF_8);
                        // 加密
                        byte[] encryptedByteArray = encryptResponse(originalResponseBody).getBytes(StandardCharsets.UTF_8);
                        originalResponse.getHeaders().setContentLength(encryptedByteArray.length);
                        originalResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                        return dataBufferFactory.wrap(encryptedByteArray);
                    }));
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(originalResponse.getHeaders());
                return headers;
            }
        };
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
