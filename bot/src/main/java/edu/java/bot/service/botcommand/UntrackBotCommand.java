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
public class UntrackBotCommand implements BotCommand {
    private final Logger logger = LogManager.getLogger();

    private final HostResourceChecker checker;

    private final UserRepository userRepo;

    @Override
    public String name() {
        return "untrack";
    }

    @SuppressWarnings("ReturnCount")
    @Override
    public SendMessage process(Update upd) {
        long id = BotHelper.getChatByUpd(upd);

        if (!userRepo.registered(id)) {
            return new SendMessage(id, "Данная команда требует регистрации!");
        }

        String[] args = upd.message().text().split(" ");
        if (args.length != 2) {
            return new SendMessage(id, "Команда требует указания единственного аргумента: ссылка на веб-страницу");
        }
        String link = args[1];

        String isBad = verifyLink(link);
        if (isBad != null) {
            return new SendMessage(id, isBad);
        }

        if (!userRepo.tracks(id, link)) {
            return new SendMessage(id, "Вы еще не отслеживаете эту ссылку.");
        }

        userRepo.removeTrack(id, link);

        logger.info(id + " unsubscribed from " + link);
        return new SendMessage(id, "Вы перестали следить за " + link + ".");
    }

    private String verifyLink(String link) {
        URL url = URLHelper.tryURL(link);
        if (url == null) {
            return "Ссылка на сайт указана неверно!";
        }
        if (!checker.isLinkSupported(url)) {
            return "Данный ресурс не поддерживается!";
        }
        return null;
    }
}
