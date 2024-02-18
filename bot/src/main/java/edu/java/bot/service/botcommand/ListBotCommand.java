package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.service.BotHelper;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class ListBotCommand implements BotCommand {
    private final Logger logger = LogManager.getLogger();

    private final UserRepository userRepo;

    @Override
    public String name() {
        return "list";
    }

    @Override
    public SendMessage process(Update upd) {
        long id = BotHelper.getChatByUpd(upd);

        if (!userRepo.registered(id)) {
            return new SendMessage(id, "Данная команда требует регистрации!");
        }

        logger.info(id + " issued /list");

        Set<String> tracked = userRepo.linksByUser(id);
        String trackedList = fmtTracked(tracked);

        return new SendMessage(id, trackedList);
    }

    public static String fmtTracked(Set<String> tracked) {
        String sep = "\n - ";
        return "Список подписок: "
            + (tracked.isEmpty() ? "пусто" : tracked.stream().collect(Collectors.joining(sep, sep, "")));
    }
}
