package io.code.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient("provider")
public interface ProviderClient {

    @RequestMapping("/provider/sayHello")
    String sayHello(@RequestParam String name);

}
