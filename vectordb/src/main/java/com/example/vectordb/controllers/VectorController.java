package com.example.vectordb.controllers;

import com.example.vectordb.services.IngestService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VectorController {

    IngestService ingestService;

    public VectorController(IngestService ingestService){
        this.ingestService = ingestService;
    }

    @GetMapping("/test")
    public String test(@RequestParam (name = "question", defaultValue = "Hello") String question){
        return "Hello, World!";
    }

    @GetMapping("/ingest")
    public String ingest(@RequestParam (name = "question", defaultValue = "Hello") String question){
        ingestService.ingest();
        return "Done!";
    }

    @GetMapping("/ask")
    public String ask(@RequestParam (name = "question", defaultValue = "Framework de java de desarrollo de aplicaciones") String question){
        List<Document> docs = ingestService.search(question);
        System.out.println("Numero de documentos:" + docs.size());
        for(int i = 0; i < docs.size(); i++) {
            System.out.println("Doc: " + docs.get(i).getText());
        }
        return "Response:";
    }
}
