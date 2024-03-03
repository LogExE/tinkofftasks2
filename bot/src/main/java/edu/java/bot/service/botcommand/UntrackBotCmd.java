package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.service.BotHelper;
import edu.java.bot.service.URLHelper;
import edu.java.bot.service.infores.HostResourceChecker;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class UntrackBotCmd implements BotCmd {
    private final Logger logger = LogManager.getLogger();

    private final HostResourceChecker checker;

    private final UserRepository userRepo;

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "отписаться от ссылки";
    }

    @Override
    public SendMessage process(Update upd) {
        long id = BotHelper.getChatByUpd(upd);

        if (!userRepo.registered(id)) {
            return new SendMessage(id, "Данная команда требует регистрации!");
        }

        String[] args = upd.message().text().split(" ");
        String isBad = checkArgs(args);
        if (isBad != null) {
            return new SendMessage(id, isBad);
        }
        String link = args[1];

        isBad = checkLink(id, link);
        if (isBad != null) {
            return new SendMessage(id, isBad);
        }

        userRepo.removeTrack(id, link);

        logger.info(id + " unsubscribed from " + link);
        return new SendMessage(id, "Вы перестали следить за " + link + ".");
    }

    private String checkArgs(String[] args) {
        if (args.length != 2) {
            return "Команда требует указания единственного аргумента: ссылка на веб-страницу";
        }
        return null;
    }

    private String checkLink(long id, String link) {
        URL url = URLHelper.tryURL(link);
        String msg = null;
        if (url == null) {
            msg = "Ссылка на сайт указана неверно!";
        } else if (!checker.isLinkSupported(url)) {
            msg = "Данный ресурс не поддерживается!";
        } else if (!userRepo.tracks(id, link)) {
            msg = "Вы еще не отслеживаете эту ссылку.";
        }
        return msg;
    }
}
