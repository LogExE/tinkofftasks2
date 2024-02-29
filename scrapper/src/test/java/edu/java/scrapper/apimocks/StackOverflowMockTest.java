package edu.java.scrapper.apimocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.clients.StackOverflowClient;
import edu.java.clients.responses.StackOverflowResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackOverflowMockTest {
    private WireMockServer server;
    private StackOverflowClient client;
    private final int mockPort = 8888;

    @BeforeEach
    public void before() {
        server = new WireMockServer(mockPort);
        server.start();

        client = new StackOverflowClient(WebClient.builder(), "http://localhost:" + mockPort);
    }

    @AfterEach
    public void after() {
        server.stop();
    }

    @Test
    public void testGitHub() {
        int questionId = 23625529;
        String respBody = """
            {
              "items": [
                {
                  "tags": [
                    "html",
                    "css"
                  ],
                  "owner": {
                    "account_id": 1395223,
                    "reputation": 15911,
                    "user_id": 1325133,
                    "user_type": "registered",
                    "accept_rate": 64,
                    "profile_image": "https://i.stack.imgur.com/t4SAF.jpg?s=256&g=1",
                    "display_name": "felix001",
                    "link": "https://stackoverflow.com/users/1325133/felix001"
                  },
                  "is_answered": true,
                  "view_count": 77,
                  "accepted_answer_id": 23626053,
                  "answer_count": 4,
                  "score": -1,
                  "last_activity_date": 1399972789,
                  "creation_date": 1399967283,
                  "last_edit_date": 1399968409,
                  "question_id": 23625529,
                  "content_license": "CC BY-SA 3.0",
                  "link": "https://stackoverflow.com/questions/23625529/vertically-aligning-divs-in-html",
                  "title": "Vertically aligning divs in html"
                }
              ],
              "has_more": false,
              "quota_max": 10000,
              "quota_remaining": 9964
            }""";
        server.stubFor(get(urlEqualTo("/questions/" + questionId))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(respBody)));

        StackOverflowResponse expected = new StackOverflowResponse(
            List.of(
                new StackOverflowResponse.Items(
                    OffsetDateTime.of(
                        LocalDateTime.ofEpochSecond(1399972789, 0, ZoneOffset.UTC),
                        ZoneOffset.UTC
                    ))

            )
        );

        StackOverflowResponse actual = client.lastUpdated(23625529);

        assertEquals(expected, actual);
    }
}
