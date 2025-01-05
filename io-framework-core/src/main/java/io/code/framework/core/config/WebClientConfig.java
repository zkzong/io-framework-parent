package io.code.framework.core.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * @Author: zongz
 * @Date: 2024-12-21
 */
@Configuration
public class WebClientConfig {

    @Value("${webclient.connectionTimeout:5000}")
    private int connectionTimeout;
    @Value("${webclient.requestTimeout:5000}")
    private int requestTimeout;
    @Value("${webclient.readTimeout:5000}")
    private int readTimeout;

    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .responseTimeout(Duration.ofMillis(requestTimeout))
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout)));

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

}
