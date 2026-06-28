package com.example.embeddings.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingsService {
    private final EmbeddingModel embeddingModel;

    public EmbeddingsService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public float[] embed(String text) {
        return embeddingModel.embed(text);
    }

    // calcular la similitud entre dos vectores de embedding
    public double cosineSimilarity(float[] a, float[] b) {
        double dot = 0, normA = 0, normB = 0;
        for(int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    // comparar dos textos directamente
    public double compare(String text1, String text2) {
        float[] embA = embed(text1);
        float[] embB = embed(text2);

        return cosineSimilarity(embA, embB);
    }
}
