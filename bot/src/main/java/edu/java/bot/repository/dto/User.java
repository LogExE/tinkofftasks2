package edu.java.bot.repository.dto;

import java.util.HashSet;
import java.util.Set;

public class User {
    long chatId;
    private final HashSet<String> trackedLinks;

    public User(long chatId) {
        this.chatId = chatId;
        trackedLinks = new HashSet<>();
    }

    public Set<String> links() {
        return trackedLinks;
    }

    public boolean tracks(String link) {
        return trackedLinks.contains(link);
    }

    public void addTrack(String link) {
        trackedLinks.add(link);
    }

    public void removeTrack(String link) {
        trackedLinks.remove(link);
    }
}
