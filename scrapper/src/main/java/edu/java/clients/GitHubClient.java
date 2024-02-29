package edu.java.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class GitHubClient {
    private final WebClient webClient;
    private final String API_URL = "https://api.github.com/";

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public GitHubClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    public GitHubClient(WebClient.Builder webClientBuilder, String baseURL) {
        this.webClient = webClientBuilder.baseUrl(baseURL).build();
    }

    public List<GitHubEvent> getEventsSince(String repo, String owner) {
        String uri = "/repos/" + repo + "/" + owner + "/events";
        // TODO: .header("if-modified-since", "")
        return jsonStringToEvents(webClient.get().uri(uri).retrieve().bodyToMono(String.class).block());
    }

    private List<GitHubEvent> jsonStringToEvents(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
