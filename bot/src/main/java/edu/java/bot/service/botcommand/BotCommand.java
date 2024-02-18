package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface BotCommand {
    String name();

    SendMessage process(Update upd);
}
