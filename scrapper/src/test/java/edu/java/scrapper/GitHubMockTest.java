package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.clients.GitHubClient;
import edu.java.clients.GitHubEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubMockTest {
    private WireMockServer server;
    private GitHubClient client;
    private int mockPort = 8888;

    @BeforeEach
    public void before() {
        server = new WireMockServer(mockPort);
        server.start();

        client = new GitHubClient(WebClient.builder(), "http://localhost:" + mockPort);
    }

    @AfterEach
    public void after() {
        server.stop();
    }

    @Test
    public void testGitHub() {
        String respBody = "[\n" +
            "  {\n" +
            "    \"id\": \"22249084964\",\n" +
            "    \"type\": \"PushEvent\",\n" +
            "    \"actor\": {\n" +
            "      \"id\": 583231,\n" +
            "      \"login\": \"octocat\",\n" +
            "      \"display_login\": \"octocat\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/octocat\",\n" +
            "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/583231?v=4\"\n" +
            "    },\n" +
            "    \"repo\": {\n" +
            "      \"id\": 1296269,\n" +
            "      \"name\": \"octocat/Hello-World\",\n" +
            "      \"url\": \"https://api.github.com/repos/octocat/Hello-World\"\n" +
            "    },\n" +
            "    \"payload\": {\n" +
            "      \"push_id\": 10115855396,\n" +
            "      \"size\": 1,\n" +
            "      \"distinct_size\": 1,\n" +
            "      \"ref\": \"refs/heads/master\",\n" +
            "      \"head\": \"7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300\",\n" +
            "      \"before\": \"883efe034920928c47fe18598c01249d1a9fdabd\",\n" +
            "      \"commits\": [\n" +
            "        {\n" +
            "          \"sha\": \"7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300\",\n" +
            "          \"author\": {\n" +
            "            \"email\": \"octocat@github.com\",\n" +
            "            \"name\": \"Monalisa Octocat\"\n" +
            "          },\n" +
            "          \"message\": \"commit\",\n" +
            "          \"distinct\": true,\n" +
            "          \"url\": \"https://api.github.com/repos/octocat/Hello-World/commits/7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"public\": true,\n" +
            "    \"created_at\": \"2022-06-09T12:47:28Z\"\n" +
            "  }\n" +
            "]";
        server.stubFor(get(urlEqualTo("/repos/octocat/Hello-World/events"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody(respBody)));

        List<GitHubEvent> expected = List.of(
            new GitHubEvent(
                "PushEvent",
                new GitHubEvent.Actor("octocat"),
                new GitHubEvent.Repo("octocat/Hello-World"),
                OffsetDateTime.of(2022, 6, 9, 12, 47, 28, 0, ZoneOffset.UTC))
        );

        List<GitHubEvent> actual = client.getEventsSince("octocat", "Hello-World");

        assertEquals(expected, actual);
    }
}
