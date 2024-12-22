package io.code.framework.example.controller.http;

import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
import io.code.framework.example.entity.req.UserDto;
import io.code.framework.example.entity.resp.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
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

        // todo 待完善
        Mono<ApiResponse<UserVo>> apiResponseMono = webClientBuilder.baseUrl("http://127.0.0.1:8080").build()
                .post()
                .uri("/test/postParam")
                .attribute("name", name)
                .attribute("age", age)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserVo>>() {
                });

        UserVo userVo = apiResponseMono.block().getData();

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

        String s = apiResponseMono.block().getData();

        return ApiResponseUtil.success(s);
    }

    @RequestMapping("/getForm")
    public ApiResponse<String> getForm(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        //// todo 待完善
        //okhttp3.RequestBody body = new FormBody.Builder()
        //        .add("name", name)
        //        .add("age", age.toString())
        //        .build();
        //
        //Request request = new Request.Builder()
        //        .url("http://127.0.0.1:8080/test/getForm?name={name}&age={age}")
        //        .get().build();
        //
        //Response response = okHttpClient.newCall(request).execute();
        //if (response.isSuccessful()) {
        //    String s = response.body().string();
        //    return JSON.parseObject(s, ApiResponse.class);
        //}
        return null;
    }

}
