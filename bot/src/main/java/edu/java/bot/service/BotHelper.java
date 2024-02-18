package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;

public class BotHelper {
    private BotHelper() {

    }

    public static long getChatByUpd(Update upd) {
        return upd.message().chat().id();
    }
}
