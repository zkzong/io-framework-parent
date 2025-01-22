package io.code.example.consumer.http;

import io.code.example.api.req.UserDto;
import io.code.example.api.resp.UserVo;
import io.code.framework.common.entity.ApiResponse;
import io.code.framework.common.entity.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 使用WebClient请求
 *
 * @author zkzong
 */
@RestController
@RequestMapping("/webclient")
@Slf4j
public class WebClientController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * 使用WebClient发送post请求，请求体为Req类型
     *
     * @param req 请求入参
     * @return Resp
     */
    @RequestMapping("/post")
    public ApiResponse<UserVo> post(@RequestBody UserDto req) {
        log.info("req = {}", req);

        Mono<ApiResponse<UserVo>> apiResponseMono = webClientBuilder.baseUrl("http://127.0.0.1:8080").build()
                .post()
                .uri("/test/post")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserVo>>() {
                });

        UserVo userVo = apiResponseMono.block().getData();

        return ApiResponseUtil.success(userVo);
    }

    @RequestMapping("/postParam")
    public ApiResponse<UserVo> postParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        // 使用queryParam
        Mono<ApiResponse<UserVo>> paramMono = webClientBuilder.baseUrl("http://127.0.0.1:8080").build()
                .post()
                .uri(builder -> builder.path("/test/postParam")
                        .queryParam("name", name)
                        .queryParam("age", age)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserVo>>() {
                });

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", name);
        map.add("age", age.toString());
        // 使用queryParams
        Mono<ApiResponse<UserVo>> paramsMono = webClientBuilder.baseUrl("http://127.0.0.1:8080").build()
                .post()
                .uri(builder -> builder.path("/test/postParam")
                        .queryParams(map)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserVo>>() {
                });

        UserVo userVo = paramsMono.block().getData();

        return ApiResponseUtil.success(userVo);
    }

    /**
     * 使用WebClient发送get请求，请求参数为name和age
     *
     * @param name 姓名
     * @param age  年龄
     * @return Resp
     */
    @RequestMapping("/getParam")
    public ApiResponse<String> getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Mono<ApiResponse<String>> apiResponseMono = webClientBuilder.baseUrl("http://127.0.0.1:8080").build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/test/getParam")
                        .queryParam("name", "{name}")
                        .queryParam("age", "{age}")
                        .build(name, age))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {
                });
        apiResponseMono = webClientBuilder.baseUrl("http://127.0.0.1:8080").build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/test/getParam")
                        .queryParam("name", name)
                        .queryParam("age", age)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {
                });

        String data = apiResponseMono.block().getData();

        return ApiResponseUtil.success(data);
    }

    @PostMapping("/path/{name}")
    public ApiResponse<String> path(@PathVariable String name) {
        log.info("name = {}", name);

        Mono<ApiResponse<String>> apiResponseMono = webClientBuilder.baseUrl("http://127.0.0.1:8080").build()
                .post()
                .uri(uriBuilder -> uriBuilder.path("/test/path/{name}")
                        .build(name))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {
                });
        String data = apiResponseMono.block().getData();

        return ApiResponseUtil.success(data);
    }

}
