package edu.java.bot.clients;

import edu.java.controller.dto.AddLinkRequest;
import edu.java.controller.dto.LinkResponse;
import edu.java.controller.dto.ListLinksResponse;
import edu.java.controller.dto.RemoveLinkRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class ScrapperClient {
    private static final String CHAT_CONTROLLER_PATH = "/tg-chat";
    private static final String LINKS_CONTROLLER_PATH = "/links";
    private static final String TG_CHAT_HEADER = "Tg-Chat-Id";

    private final WebClient client;

    public ScrapperClient(WebClient.Builder builder, String baseURL) {
        client = builder.baseUrl(baseURL).build();
    }

    void registerChat(long chatId) {
        String uri = CHAT_CONTROLLER_PATH + "/" + chatId;
        client
            .post()
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class);
    }

    void deleteChat(long chatId) {
        String uri = CHAT_CONTROLLER_PATH + "/" + chatId;
        client
            .delete()
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class);
    }

    Set<URI> getAllTrackedLinks(long chatId) {
        ListLinksResponse resp = client
            .get()
            .uri(LINKS_CONTROLLER_PATH)
            .header(TG_CHAT_HEADER, Long.toString(chatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
        return resp.links().stream().map(LinkResponse::url).collect(Collectors.toSet());
    }

    void addTrackedLink(long chatId, String link) {
        URI url;
        try {
            url = new URI(link);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        client
            .post()
            .uri(LINKS_CONTROLLER_PATH)
            .header(TG_CHAT_HEADER, Long.toString(chatId))
            .bodyValue(new AddLinkRequest(url))
            .retrieve();
    }

    void deleteTrackedLink(long chatId, String link) {
        URI url;
        try {
            url = new URI(link);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        client
            .method(HttpMethod.DELETE)
            .uri(LINKS_CONTROLLER_PATH)
            .header(TG_CHAT_HEADER, Long.toString(chatId))
            .bodyValue(new RemoveLinkRequest(url))
            .retrieve();
    }
}
