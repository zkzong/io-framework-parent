package io.code.example.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "example-provider")
public interface ProviderClient {

    @RequestMapping("/provider/sayHello")
    String sayHello(@RequestParam String name);

}
