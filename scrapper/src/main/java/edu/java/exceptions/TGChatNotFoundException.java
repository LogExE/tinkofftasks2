package edu.java.exceptions;

public class TGChatNotFoundException extends Exception {
    private final long chatId;

    public TGChatNotFoundException(long chatId) {
        this.chatId = chatId;
    }

    public long chatId() {
        return chatId;
    }
}
