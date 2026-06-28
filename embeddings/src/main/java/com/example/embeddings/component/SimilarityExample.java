package com.example.embeddings.component;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SimilarityExample {

    private final com.example.embeddings.service.EmbeddingsService embeddingsService;

    public SimilarityExample(com.example.embeddings.service.EmbeddingsService embeddingsService) {
        this.embeddingsService = embeddingsService;
    }

    public void run(String query) {
        // String query = "¿Qué es una red neuronal?";
        // String query = "¿Cúal es la mejor comida italiana?";
        // String query = "¿Cúal es  el mejor auto?";

        List<String> candidates = List.of(
                "redes neuronales y deep learning",
                "receta de cocina italiana the spagetti",
                "modelos de inteligencia artificial",
                "modelos de machine learning",
                "un framework para el desarrollo de agentes en Java es Embabel",
                "clasificación de clientes por nivel de compra en restaurantes españoles",
                "el ferrari es el carro más rápido del mundo",
                "el BYD es el mejor carro eléctrico",
                "el mejor plato yucateco es la cochinita pibil",
                "el pacer fue el carro más peligroso",
                "La inteligencia artificial ha cambiado la forma de programar",
                "el pacer fue el carro más veloz",
                "un framework para desarrollo de agentes es crewai",
                "El mejor libro de Deep Learning es el de Patterson $ Gibson",
                "La base de datos de vectores opensource es Postgresql",
                "el flan napolitano es fácil de hacer"
        );

        float[] queryEmbeddings = embeddingsService.embed(query);

        candidates.stream()
                .map(text -> Map.entry(text, embeddingsService.cosineSimilarity(
                        queryEmbeddings,
                        embeddingsService.embed(text)
                )))
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.printf("%.4f  %s\n", e.getValue(), e.getKey()));
                System.out.printf("Done!\n\n");
    }
}
