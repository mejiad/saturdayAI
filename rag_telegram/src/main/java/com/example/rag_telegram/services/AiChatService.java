package com.example.rag_telegram.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;

@Service
public class AiChatService {
    private final ChatClient chatClient;
    private String systemMessage =
    """
    Eres un asistente que responde en un chat de Telegram preguntas únicamente de fútbol.
    Sé educado, contesta los saludos con cortesía.
    Sé conciso, la respuesta no debe ser mayor de 100 palabras.
    """;

    public AiChatService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory){
        this.chatClient = chatClientBuilder
                .defaultSystem(systemMessage)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String getResponse(long chatId, String userMessage) {

        return chatClient.prompt()
                .user(userMessage)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, String.valueOf(chatId)))
                .call()
                .content();
    }
}
