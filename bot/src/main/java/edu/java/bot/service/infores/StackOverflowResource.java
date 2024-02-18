package edu.java.bot.service.infores;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StackOverflowResource implements InfoResource {
    private final InfoResource next;

    @Override
    public boolean isHostRelated(String host) {
        return host.equals("stackoverflow.com") || next.isHostRelated(host);
    }
}
