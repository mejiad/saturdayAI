package com.example.rag.controllers;

import com.example.rag.services.QuestionService;
import com.example.rag.services.ReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RagController {
    Logger logger = LoggerFactory.getLogger(RagController.class);

    private final QuestionService questionService;
    private final ReaderService readerService;
    private final Resource resource;


    public RagController(ReaderService readerService, QuestionService questionService, @Value("classpath:/PDF_DIRECTORY/fifa.pdf") Resource resource){
        this.resource = resource;
        this.readerService = readerService;
        this.questionService = questionService;
    }

    @GetMapping("/ingest")
    public String ingest(){
        int no_docs = readerService.coordinator();
        return "Added " + no_docs + " docs";
    }

    @GetMapping("/ask")
    public String ask(@RequestParam (name = "question", defaultValue = "En el futbol,¿Qué es el saque de banda?") String question){
        String response = questionService.run("futbol", question);
        logger.info( "Response:" + response);
        return "Response:" + response;
    }

    @GetMapping("/askAdvisor")
    public String askAdvisor(@RequestParam (name = "question", defaultValue = "En el futbol,¿Qué es el saque de banda?") String question){
        String response = questionService.run("futbol", question);
        logger.info( "Response:" + response);
        return "Response:" + response;
    }

}
