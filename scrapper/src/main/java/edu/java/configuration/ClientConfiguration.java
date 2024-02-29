package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean GitHubClient gitHubClient() {
        return new GitHubClient(WebClient.builder());
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(WebClient.builder());
    }
}
