package com.example.reader.controller;

import com.example.reader.services.ReaderService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReaderController {

    ReaderService readerService;

    @Autowired
    ReaderController(ReaderService readerService ){
        this.readerService = readerService;
    }

    @GetMapping("/ai/reader")
    public String reader(){
        StringBuilder strBuilder = new StringBuilder();
       List<Document> docs = readerService.readFile();
       List<String> docText = docs.stream()
               .map(Document::getText)
               .toList();

       for (Document d : docs){
           String text = null;
           if (d.getText() != null) {
               text = d.getText().replaceAll("[\s]+", " ");
           }
           strBuilder.append(text);
        }

       return strBuilder.toString();
    }
}
