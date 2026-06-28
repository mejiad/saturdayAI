package com.ai101.stream.controller;

import com.ai101.stream.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaController {

    ChatService chatService;

    @Autowired
    public OllamaController(ChatService _service) {
        chatService = _service;
    }

    @GetMapping("/test")
    public String GetJoke(){
        System.out.print("Hello World");;
        return "Hello, World!";
    }

    @GetMapping("/ai/jokes")
    public String AiJokes(){
        String response = chatService.run("Dime un chiste.");
       return response;
    }

    @PostMapping("/ai/chat")
    public String AiChat(@RequestParam(value = "message", defaultValue = "Dime un chiste") String message){
        String response = chatService.run(message);
        return response;
    }

    @PostMapping("/ai/template")
    public String template( @RequestParam(value = "sport", defaultValue = "chess") String sport,
            @RequestParam(value = "question", defaultValue = "Dime como se mueve la torre") String question){
        String response = chatService.run(sport, question);
        return response;
    }

    @PostMapping("/ai/value")
    public String templateValue( @RequestParam(value = "sport", defaultValue = "chess") String sport,
                            @RequestParam(value = "question", defaultValue = "Dime como se mueve la torre") String question){
        String response = chatService.runTemplate(sport, question);
        return response;
    }
}

