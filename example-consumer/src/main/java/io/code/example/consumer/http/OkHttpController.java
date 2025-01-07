package io.code.example.consumer.http;

import com.alibaba.fastjson2.JSON;
import io.code.example.api.req.UserDto;
import io.code.example.api.resp.UserVo;
import io.code.framework.core.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 使用OkHttp请求
 *
 * @author zkzong
 */
@RestController
@RequestMapping("/okhttp")
@Slf4j
public class OkHttpController {

    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * 使用OkHttp发送post请求，请求体为Req类型
     *
     * @param req 请求入参
     * @return Resp
     */
    @RequestMapping("/post")
    public ApiResponse<UserVo> post(@RequestBody UserDto req) throws IOException {
        log.info("req = {}", req);

        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(req));

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/test/post")
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String s = response.body().string();
            return JSON.parseObject(s, ApiResponse.class);
        }
        return null;
    }

    @RequestMapping("/postParam")
    public ApiResponse<UserVo> postParam(@RequestParam String name, @RequestParam Integer age) throws IOException {
        log.info("name = {}, age = {}", name, age);

        okhttp3.RequestBody body = new FormBody.Builder()
                .add("name", name)
                .add("age", age.toString())
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/test/postParam?name={name}&age={age}")
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String s = response.body().string();
            return JSON.parseObject(s, ApiResponse.class);
        }
        return null;
    }

    /**
     * 使用OkHttp发送get请求，请求参数为name和age
     *
     * @param name 姓名
     * @param age  年龄
     * @return Resp
     */
    @RequestMapping("/getParam")
    public ApiResponse<String> getParam(@RequestParam String name, @RequestParam Integer age) throws IOException {
        log.info("name = {}, age = {}", name, age);

        // todo 待完善
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("name", name)
                .add("age", age.toString())
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/test/getParam?name={name}&age={age}")
                .get().build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String s = response.body().string();
            return JSON.parseObject(s, ApiResponse.class);
        }
        return null;
    }

    @RequestMapping("/getForm")
    public ApiResponse<String> getForm(@RequestParam String name, @RequestParam Integer age) throws IOException {
        log.info("name = {}, age = {}", name, age);

        // todo 待完善
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("name", name)
                .add("age", age.toString())
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/test/getForm?name={name}&age={age}")
                .get().build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String s = response.body().string();
            return JSON.parseObject(s, ApiResponse.class);
        }
        return null;
    }

    /**
     * 使用OkHttp发送get请求，请求参数为json格式
     *
     * @param req 请求入参
     * @return Resp
     */
    @RequestMapping("/getBody")
    public ApiResponse<String> getBody(@RequestBody UserDto req) throws IOException {
        log.info("req = {}", req);

        // todo 待完善
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(req));

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/test/getBody")
                .get().build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String s = response.body().string();
            return JSON.parseObject(s, ApiResponse.class);
        }
        return null;
    }

}
