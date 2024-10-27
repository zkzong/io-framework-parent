package com.example.framework.example.controller;

import com.example.framework.core.req.Req;
import com.example.framework.core.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用RestTemplate请求
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
     * @param req
     * @return
     */
    @RequestMapping("/post")
    public Resp<Req> post(@RequestBody Req req) {
        log.info("req = {}", req);

        HttpEntity<Req> entity = new HttpEntity<>(req);

        return restTemplate.postForObject("http://127.0.0.1:8888/test/post", entity, Resp.class);
    }

    /**
     * 使用RestTemplate发送post请求，请求参数为name和age
     *
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/getParam")
    public Resp<String> getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("name = {}, age = {}", name, age);

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map);

        return restTemplate.postForObject("http://127.0.0.1:8888/test/getParam", entity, Resp.class);
    }

}
