package edu.java.clients;

import edu.java.clients.responses.GitHubResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient extends AbstractClient {

    public GitHubClient(WebClient.Builder webClientBuilder, String baseURL) {
        super(webClientBuilder, baseURL);
    }

    public GitHubResponse lastUpdated(String owner, String repo) {
        String uri = "/repos/" + owner + "/" + repo;
        return webClient
            .get()
            .uri(uri)
            .header("accept", "application/vnd.github+json")
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }
}
