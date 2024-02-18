package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.service.BotHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class RegisterBotCommand implements BotCommand {
    private final Logger logger = LogManager.getLogger();

    private final UserRepository userRepo;

    @Override
    public String name() {
        return "start";
    }

    @Override
    public SendMessage process(Update upd) {
        long id = BotHelper.getChatByUpd(upd);

        if (userRepo.registered(id)) {
            return new SendMessage(id, "Вы уже зарегистрированы.");
        }

        userRepo.registerUser(id);
        logger.info(id + " registered");
        return new SendMessage(id, "Вы успешно зарегистрировались.");
    }
}
