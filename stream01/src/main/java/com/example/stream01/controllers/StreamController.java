package com.example.stream01.controllers;

import com.example.stream01.services.StreamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamController {

    StreamService streamService;

    StreamController(StreamService streamService){
        this.streamService = streamService;
    }

    @GetMapping("/ask")
    public String  askGet(@RequestParam(value = "message", defaultValue = "Cuéntame un chiste") String message) {
        return streamService.askQuestion(message);
    }

    @GetMapping(value = "/stream", produces = "application/ndjson")
    public Flux<String> stream(@RequestParam(value = "message",
            defaultValue = "Dame algunas recomendaciones para seleccionar un modelo LLM") String message) {
        return streamService.stream(message);
    }

}
