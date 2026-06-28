package com.ai101.stream.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    ChatClient chatClient;

    private static final String promptTemplate = """
            Eres un asistente para resolver preguntas sobre las reglas de {deporte}.
            Si no conoces la respuesta o la pregunta no es sobre el {deporte}, 
            solo responde "No tengo permiso para responder esa pregunta"
            
            El deporte de la pregunta es {deporte}
            
            La pregunta es: {pregunta}
            """;

    @Value("classpath:/promptTemplates/questionSports.st")
    Resource questionPrompt;

    public ChatService(ChatClient.Builder builder){
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

    public String run(String sport, String question){
        String answer = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(promptTemplate)
                        .param("deporte", sport )
                        .param("pregunta", question))
                .call()
                .content();

        return answer;
    }

    public String runTemplate(String sport, String question){
        String answer = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(questionPrompt)
                        .param("deporte", sport )
                        .param("pregunta", question))
                .call()
                .content();

        return answer;
    }
}

