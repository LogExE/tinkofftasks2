package edu.java.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowResponse(
    @JsonProperty("items") List<Items> items
) {
    public record Items(
        @JsonProperty("last_activity_date") OffsetDateTime lastEditDate
    ) {
    }
}
