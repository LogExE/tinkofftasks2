package edu.java.clients;

import edu.java.controller.dto.LinkUpdate;
import java.net.URI;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public class TGBotClient {
    private static final String UPDATES_CONTROLLER_PATH = "/updates";
    private final WebClient webClient;

    public TGBotClient(WebClient.Builder webClientBuilder, String baseURL) {
        webClient = webClientBuilder.baseUrl(baseURL).build();
    }

    void notifyUpdate(
        long id,
        URI url,
        String description,
        List<Long> tgChatIds
    ) {
        webClient
            .post()
            .uri(UPDATES_CONTROLLER_PATH)
            .bodyValue(new LinkUpdate(id, url, description, tgChatIds))
            .retrieve();
    }
}
