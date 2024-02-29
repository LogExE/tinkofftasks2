package edu.java.clients;

import edu.java.clients.responses.StackOverflowResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient extends AbstractClient {
    public StackOverflowClient(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
    }

    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseURL) {
        super(webClientBuilder, baseURL);
    }

    @Override
    protected String defaultApiUrl() {
        return "https://api.stackexchange.com/";
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
