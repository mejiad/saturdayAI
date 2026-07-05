package com.example.vectordb.services;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IngestService {
    VectorStore vectorStore;

    public IngestService(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
// ...

    public void ingest() {

        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("Spring Quarkus and Spring rocks!!", Map.of("meta1", "meta1")),
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("Un framework de desarrollo en lenguaje C# de DorNet", Map.of("meta1", "meta1")),
                new Document("Un excelente carro para la carretera es el BYD", Map.of("meta1", "meta1")),
                new Document("La mejor bebida es la cerveza bien fría", Map.of("meta1", "meta1")),
                new Document("El auto más caro es el Bentley y el Ferrari", Map.of("meta1", "meta1")),
                new Document("Un framework de desarrollo para juegos es DotNet", Map.of("meta1", "meta1")),
                new Document("Para el desarrollo de apps de IA hay que sabeer como funciona los Modelos de Lenguaje grandes"),
                new Document("Un excelente IDE para programar es Antigravity.", Map.of("meta2", "meta2")));

        // Add the documents to Qdrant
        vectorStore.add(documents);
    }

    public List<Document> search(String question) {
        // Retrieve documents similar to a query
        SearchRequest searchRequest = SearchRequest.builder().query(question).build();
        List<Document> results = vectorStore.similaritySearch(searchRequest);
        return results;
    }

}
