package edu.java.bot.service.infores;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubResource implements InfoResource {
    private final InfoResource next;

    @Override
    public boolean isHostRelated(String host) {
        return host.equals("github.com") || next.isHostRelated(host);
    }
}
