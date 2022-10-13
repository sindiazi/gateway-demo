package com.samsteel.gatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class GatewayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayDemoApplication.class, args);
    }

    @GetMapping("/")
    public Mono<Map<String,Object>> test(@AuthenticationPrincipal OAuth2AuthenticationToken token) {
        return Mono.fromCallable(() -> new TreeMap<String,Object>(){{
            put("First name", token.getPrincipal().getAttributes().get("given_name"));
            put("Last name", token.getPrincipal().getAttributes().get("family_name"));
            put("Full name", token.getPrincipal().getAttributes().get("name"));
            put("Email", token.getPrincipal().getAttributes().get("email"));
        }});
    }
}
