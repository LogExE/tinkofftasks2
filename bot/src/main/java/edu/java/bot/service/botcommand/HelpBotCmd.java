package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.BotHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelpBotCmd implements BotCmd {
    private final Logger logger = LogManager.getLogger();

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "получить справочную информацию о боте";
    }

    @Override
    public SendMessage process(Update upd) {
        long id = BotHelper.getChatByUpd(upd);

        String isBad = checkArgs(upd.message().text().split(" "));
        if (isBad != null) {
            return new SendMessage(id, isBad);
        }

        logger.info(id + " issued /help");
        return new SendMessage(id, """
            Для начала работы с ботом зарегистрируйтесь: /start

            После регистрации вы можете подписаться на интересующие вас обновления командой /track <ссылка>
            В будущем от них можно отписаться командой /untrack
            Список подписок можно посмотреть так: /list
            """);
    }

    private String checkArgs(String[] args) {
        if (args.length != 1) {
            return "Данная команда не принимает никаких аргументов.";
        }
        return null;
    }
}
