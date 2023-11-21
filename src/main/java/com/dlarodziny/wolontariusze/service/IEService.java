package com.dlarodziny.wolontariusze.service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class IEService {
    @Value("${api:localhost}")
    private String myApi;

    @Value(value = "${my.port:328}")
    private String myUrl;

    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected(conn -> 
            conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    private WebClient webClient() {
        myApi = myApi == null || myApi.isBlank() ? "localhost" : myApi;
        myUrl = myUrl == null || myUrl.isBlank() ? "328" : myUrl;
        return WebClient.builder()
        .baseUrl(String.format("http://%s:%s", myApi, myUrl))
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
        .clientConnector(new ReactorClientHttpConnector(this.httpClient))
        .build();
    }

    public Mono<String> getVolunteers(final String id, final String fold, final String scope) {
        // @RequestParam String id, @RequestParam String fold, @RequestParam String scope
        return webClient().get()
            .uri(uriBuilder -> uriBuilder
                .path("/getVolunteers")
                .queryParam("id", "{id}")
                .queryParam("fold", "{fold}")
                .queryParam("scope", "{scope}")
                .build(id, fold, scope))
            .exchangeToMono(response -> {
                if (response.statusCode().equals(HttpStatus.OK)) {
                    return response.bodyToMono(String.class);
                } else if (response.statusCode().is4xxClientError()) {
                    return Mono.just("Client Error");
                } else if (response.statusCode().is5xxServerError()) {
                    return Mono.just("Server Error");
                } else {
                    return Mono.just("Other Error");
                }
            });
    }

    public Mono<String> getContacts(final String id, final String fold, final String scope) {
        return webClient().get()
            .uri(uriBuilder -> uriBuilder
                .path("/getContacts")
                .queryParam("id", "{id}")
                .queryParam("fold", "{fold}")
                .queryParam("scope", "{scope}")
                .build(id, fold, scope))
            .exchangeToMono(response -> {
                if (response.statusCode().equals(HttpStatus.OK)) {
                    return response.bodyToMono(String.class);
                } else if (response.statusCode().is4xxClientError()) {
                    return Mono.just("Client Error");
                } else if (response.statusCode().is5xxServerError()) {
                    return Mono.just("Server Error");
                } else {
                    return Mono.just("Other Error");
                }
            });
    }
    
}