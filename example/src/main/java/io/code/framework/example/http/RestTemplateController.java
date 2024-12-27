package io.code.framework.example.http;

import io.code.framework.core.entity.ApiResponse;
import io.code.framework.example.demo.entity.req.UserDto;
import io.code.framework.example.demo.entity.resp.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用RestTemplate请求
 *
 * @author zkzong
 */
@RestController
@RequestMapping("/rest")
@Slf4j
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 使用RestTemplate发送post请求，请求体为Req类型
     *
     * @param req 请求入参
     * @return Resp
     */
    @RequestMapping("/post")
    public ApiResponse<UserVo> post(@RequestBody UserDto req) {
        log.info("req = {}", req);

        HttpEntity<UserDto> entity = new HttpEntity<>(req);
        return restTemplate.postForObject("http://127.0.0.1:8080/test/post", entity, ApiResponse.class);
    }

    @RequestMapping("/postParam")
    public ApiResponse<UserVo> postParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);

        return restTemplate.postForObject("http://127.0.0.1:8080/test/postParam?name={name}&age={age}", null, ApiResponse.class, map);
    }

    /**
     * 使用RestTemplate发送get请求，请求参数为name和age
     *
     * @param name 姓名
     * @param age  年龄
     * @return Resp
     */
    @RequestMapping("/getParam")
    public ApiResponse<String> getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);

        return restTemplate.getForObject("http://127.0.0.1:8080/test/getParam?name={name}&age={age}", ApiResponse.class, map);
    }

    /**
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/getForm")
    public ApiResponse<String> getForm(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);

        return restTemplate.getForObject("http://127.0.0.1:8080/test/getForm?name={name}&age={age}", ApiResponse.class, map);
    }

    /**
     * 使用RestTemplate发送get请求，请求参数为json格式
     * 需要升级到spring 6以上，否则会报错
     * RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
     * https://stackoverflow.com/questions/62309635/resttemplate-get-with-body
     *
     * @param req 请求入参
     * @return Resp
     */
    @RequestMapping("/getBody")
    public ApiResponse<String> getBody(@RequestBody UserDto req) {
        log.info("req = {}", req);

        HttpHeaders headers = new HttpHeaders();
        // 设置请求头为json格式
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(req, headers);

        RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
        ResponseEntity<ApiResponse> exchange = restTemplate.exchange("http://127.0.0.1:8080/test/getBody", HttpMethod.GET, httpEntity, ApiResponse.class);
        return exchange.getBody();
    }

}
