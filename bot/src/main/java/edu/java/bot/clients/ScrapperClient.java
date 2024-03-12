package edu.java.bot.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Set;

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

    Set<String> getAllTrackedLinks(long chatId) {
        client
            .get()
            .uri(LINKS_CONTROLLER_PATH)
            .header(TG_CHAT_HEADER, Long.toString(chatId))
            .retrieve()
            .bodyToMono(String.class);
    }

    void addTrackedLink() {

    }

    void deleteTrackedLink() {

    }
}
