package edu.java.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubEvent(
    @JsonProperty("type") String type,
    @JsonProperty("actor") Actor actor,
    @JsonProperty("repo") Repo repo,
    @JsonProperty("created_at") OffsetDateTime createdAt
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Actor(@JsonProperty("login") String login) {

    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Repo(@JsonProperty("name") String name) {

    }
}
