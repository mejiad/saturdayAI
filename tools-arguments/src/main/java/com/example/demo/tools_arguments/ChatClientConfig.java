package com.example.demo.tools_arguments;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder.build();
    }

    @Bean
    ChatClientBuilderCustomizer safeGuardAdvisor(){
        System.out.println("Creacion de safe guard advisort");
        return builder -> {
            builder.defaultAdvisors(
                    SafeGuardAdvisor
                        .builder()
                            .failureResponse("No se permiten estas palabras!")
                                .sensitiveWords(List.of("Negro", "tonto", "nazi")
                                )
                                .build())
                        .build();
        };
    }

    @Bean
    ChatClientBuilderCustomizer chatMemoryCustomizer2(){
        System.out.println("Creacion de customizer 2");
        return builder -> {
            builder.defaultAdvisors(
                    MessageChatMemoryAdvisor
                            .builder(MessageWindowChatMemory.builder()
                                    .maxMessages(500)
                                    .build())
                            .build());
        };
    }

}
