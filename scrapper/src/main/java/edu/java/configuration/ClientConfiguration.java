package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Value("${app.api-urls.stackoverflow}")
    private String stackoverflowURL;
    @Value("${app.api-urls.github}")
    private String githubURL;

    @Bean GitHubClient gitHubClient() {
        return new GitHubClient(WebClient.builder(), githubURL);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(WebClient.builder(), stackoverflowURL);
    }
}
