package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.botcommand.BotCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ScraperTGBot {
    Logger logger = LogManager.getLogger();
    private final TelegramBot bot;

    public ScraperTGBot(ApplicationConfig applicationConfig, BotCommandHandler comHandler) {
        bot = new TelegramBot(applicationConfig.telegramToken());

        bot.execute(new SetMyCommands(comHandler.commands().toArray(new BotCommand[0])));
        bot.setUpdatesListener(updates -> {
            for (var upd : updates) {
                if (upd == null || upd.message() == null || upd.message().text() == null) {
                    continue;
                }
                SendMessage res = comHandler.handle(upd);
                bot.execute(res);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                logger.error(e.response().errorCode());
                logger.error(e.response().description());
            } else {
                logger.error(e.getMessage());
            }
        });
    }
}
