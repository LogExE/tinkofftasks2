package edu.java.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponse(
    @JsonProperty("updated_at") OffsetDateTime updatedAt
) {
}
