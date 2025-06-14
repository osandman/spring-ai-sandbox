package net.osandman.ai.sandbox.api;

import chat.giga.client.GigaChatClient;
import chat.giga.model.Balance;
import chat.giga.model.ModelResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/giga", produces = MediaType.APPLICATION_JSON_VALUE)
public class GigaController {

    private final GigaChatClient gigaChatClient;

    public GigaController(GigaChatClient gigaChatClient) {
        this.gigaChatClient = gigaChatClient;
    }

    @GetMapping("/balance")
    public List<Balance> getBalance() {
        return gigaChatClient.balance().balance();
    }

    @GetMapping("/models")
    public ModelResponse getModels() {
        return gigaChatClient.models();
    }
}
