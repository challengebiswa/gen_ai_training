package com.epam.training.gen.ai.service;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatService {

    private final ChatCompletionService chatCompletionService;
    private final InvocationContext invocationContext;

    ChatHistory chatHistory = new ChatHistory();

    public ChatService(ChatCompletionService chatCompletionService, InvocationContext invocationContext) {
        this.chatCompletionService = chatCompletionService;
        this.invocationContext = invocationContext;
        this.chatHistory.addSystemMessage("""
                    You are an assistant designed to help customers with placing orders from our menu.
                    Available menu items include:
                    - Pizza
                    - Pasta
                    - Salad
    
                    Please assist with taking orders, answering questions about the menu, and ensuring a smooth ordering experience.
                """);
    }

    public String getChatCompletions(String prompt) {
        Kernel kernel = createKernel(chatCompletionService);

        chatHistory.addUserMessage(prompt);
        List<ChatMessageContent<?>> result = chatCompletionService.getChatMessageContentsAsync(
                chatHistory,
                kernel,
                invocationContext
        ).block();

        if (result == null || result.isEmpty()) {
            return "No response received from the assistant.";
        }

        chatHistory.addAssistantMessage(result.get(0).getContent());

        printChatHistory();

        return result.get(0).getContent();
    }

    public void printChatHistory() {
        System.out.println("Chat History:");
        chatHistory.forEach(chatMessageContent -> {
            String role = chatMessageContent.getAuthorRole().toString().toLowerCase();
            String content = chatMessageContent.getContent();
            System.out.printf("%s: %s%n", capitalize(role), content);
        });
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private Kernel createKernel(ChatCompletionService chatCompletionService) {
        return Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .build();
    }
}
