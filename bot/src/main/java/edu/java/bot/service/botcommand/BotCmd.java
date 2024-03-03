package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface BotCmd {
    String name();

    String description();

    SendMessage process(Update upd);

    default BotCommand toApiCommand() {
        return new BotCommand(name(), description());
    }
}
