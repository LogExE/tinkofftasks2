package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
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

        bot.setUpdatesListener(updates -> {
            for (var upd : updates) {
                if (upd.message() == null) {
                    continue;
                }
                String txt = upd.message().text();
                SendMessage res;
                if (txt.startsWith("/")) {
                    res = comHandler.handle(upd);
                } else {
                    res = complainUser(upd);
                }
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

    private SendMessage complainUser(Update upd) {
        long id = BotHelper.getChatByUpd(upd);
        return new SendMessage(
            id,
            "Пожалуйста, введите команду. Для просмотра доступных комманд отправьте \"/help\" без кавычек."
        );
    }
}
