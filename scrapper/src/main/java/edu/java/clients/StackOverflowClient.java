package edu.java.clients;

import edu.java.clients.responses.StackOverflowResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseURL) {
        webClient = webClientBuilder.baseUrl(baseURL).build();
    }

    public StackOverflowResponse lastUpdated(long id) {
        String uri = "/questions/" + id;
        return webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .block();
    }
}
