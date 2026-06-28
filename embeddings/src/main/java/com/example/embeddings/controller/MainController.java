package com.example.embeddings.controller;

import com.example.embeddings.component.SimilarityExample;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private final EmbeddingModel embeddingModel;
    private final SimilarityExample similarityExample;

    @Autowired
    public MainController(EmbeddingModel embeddingModel, SimilarityExample example) {
        this.embeddingModel = embeddingModel;
        this.similarityExample = example;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }

    @GetMapping("/ai/similarity")
    public String  similarity(@RequestParam(value = "message", defaultValue = "Cuál es la mejor comida italiana?") String message){
        similarityExample.run( message);
        return "Done!";
    }

    @PostMapping("/ai/post")
    public String  similarity2(@RequestParam(value = "message", defaultValue = "Mejores libros para aprender IA") String message){
        similarityExample.run(message);
        return "Done!";
    }

}