package com.example.framework.example.controller;

import com.example.framework.core.req.Req;
import com.example.framework.core.resp.Resp;
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
    public Resp<Req> post(@RequestBody Req req) {
        log.info("req = {}", req);

        HttpEntity<Req> entity = new HttpEntity<>(req);
        return restTemplate.postForObject("http://127.0.0.1:8888/test/post", entity, Resp.class);
    }

    @RequestMapping("/postParam")
    public Resp<Req> postParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);

        return restTemplate.postForObject("http://127.0.0.1:8888/test/postParam?name={name}&age={age}", null, Resp.class, map);
    }

    /**
     * 使用RestTemplate发送get请求，请求参数为name和age
     *
     * @param name 姓名
     * @param age  年龄
     * @return Resp
     */
    @RequestMapping("/getParam")
    public Resp<String> getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);

        return restTemplate.getForObject("http://127.0.0.1:8888/test/getParam?name={name}&age={age}", Resp.class, map);
    }

    @RequestMapping("/getForm")
    public Resp<String> getForm(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);

        return restTemplate.getForObject("http://127.0.0.1:8888/test/getForm?name={name}&age={age}", Resp.class, map);
    }

    /**
     * 使用RestTemplate发送get请求，请求参数为json格式
     * 需要升級到spring 6以上，否则会报错
     * RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
     *
     * @param req 请求入参
     * @return Resp
     */
    @RequestMapping("/getBody")
    public Resp<String> getBody(@RequestBody Req req) {
        log.info("req = {}", req);

        HttpHeaders headers = new HttpHeaders();
        // 设置请求头为json格式
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Req> httpEntity = new HttpEntity<>(req, headers);

        RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
        ResponseEntity<Resp> exchange = restTemplate.exchange("http://127.0.0.1:8888/test/getBody", HttpMethod.GET, httpEntity, Resp.class);
        return exchange.getBody();
    }

}
