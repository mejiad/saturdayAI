package com.example.rag.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    VectorStore vectorStore;
    ChatClient chatClient;

    private static final String promptTemplate = """
            Eres un asistente para resolver preguntas sobre las reglas de futbol.
            Si no conoces la respuesta o la pregunta no es sobre el futbol, 
            solo responde "No tengo datos para responder esa pregunta."
            
            El deporte de la pregunta es del futbol.
            
            La pregunta es: {pregunta}
            """;

    @Value("classpath:/promptTemplates/questionSports.st")
    Resource questionPrompt;

    public QuestionService(ChatClient.Builder builder, VectorStore vectorStore){
        this.vectorStore = vectorStore;
        chatClient = builder
                .defaultSystem("Cada vez que te pidan un chiste respondes con la frase: Solo se aceptan preguntas serias")
                .build();
    }

    public String run(String message){
        String answer = chatClient.prompt()
                .user(message)
                .call()
                .content();
        return answer;
    }

    public String runAdviser(String sport, String question){
        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .build();

        String answer = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(promptTemplate)
                        .param("pregunta", question))
                .advisors(qaAdvisor)
                .call()
                .content();

        return answer;
    }

    public String run(String sport, String question){
        String answer = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(promptTemplate)
                        .param("pregunta", question))
                .call()
                .content();

        return answer;
    }

    public String runTemplate(String sport, String question){
        String answer = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(questionPrompt)
                        .param("pregunta", question))
                .call()
                .content();

        return answer;
    }


}
