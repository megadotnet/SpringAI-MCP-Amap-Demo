package com.mcp.test.mcpdemo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/llm")
public class LLMController {
    private final ChatClient chatClient;
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public LLMController(ChatClient.Builder builder) {
        this.chatClient = builder.build();;
    }

   // @GetMapping(path = "/stream", produces = MediaType.TEXT_HTML_VALUE)
   // public Flux<String> helloStream(@RequestParam(name = "input", defaultValue = "AI编程趋势是什么") String input) {
   //     return chatClient.prompt(input)
   //             .stream()
   //             .content();
   // }

    @GetMapping
    public String hello(@RequestParam(name = "input", defaultValue = "AI编程趋势是什么") String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input cannot be empty");
        }
        if (input.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input is too long");
        }
        return cache.computeIfAbsent(input, k -> chatClient.prompt(k).call().content());
    }
}
