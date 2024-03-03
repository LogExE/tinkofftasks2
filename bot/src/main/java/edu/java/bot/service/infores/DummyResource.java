package edu.java.bot.service.infores;

public class DummyResource implements InfoResource {
    @Override
    public boolean isHostRelated(String host) {
        return false;
    }
}
