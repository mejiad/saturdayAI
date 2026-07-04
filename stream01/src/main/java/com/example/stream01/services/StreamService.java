package com.example.stream01.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StreamService {
    ChatClient chatClient;

    StreamService(ChatClient.Builder chatClientBuilder){
        chatClient = chatClientBuilder.build();
    }

    public String askQuestion(String question)
    {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    public Flux<String> stream(String question)
    {
        return chatClient.prompt()
                .user(question)
                .stream()
                .content();
    }
}
