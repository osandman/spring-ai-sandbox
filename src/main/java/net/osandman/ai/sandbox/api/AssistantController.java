package net.osandman.ai.sandbox.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    private final ChatClient chatClient;

    public AssistantController(@Qualifier("defaultChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping
    public String getAnswer(
        @RequestParam(required = false) String system,
        @RequestParam String question
    ) {
        ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt().user(question);
        if (hasText(system)) {
            requestSpec.system(system);
        }
        return requestSpec.call().content();
    }

}
