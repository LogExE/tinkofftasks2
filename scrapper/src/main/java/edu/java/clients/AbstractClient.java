package edu.java.clients;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractClient {
    protected final WebClient webClient;

    protected abstract String defaultApiUrl();

    public AbstractClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(defaultApiUrl()).build();
    }

    public AbstractClient(WebClient.Builder webClientBuilder, String baseURL) {
        this.webClient = webClientBuilder.baseUrl(baseURL).build();
    }
}
