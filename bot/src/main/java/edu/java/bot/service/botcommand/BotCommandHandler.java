package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.service.BotHelper;
import edu.java.bot.service.infores.HostResourceChecker;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class BotCommandHandler {
    private final HashMap<String, BotCommand> nameToCmd = new HashMap<>();

    public BotCommandHandler(UserRepository userRepo, HostResourceChecker checker) {
        List<BotCommand> cmds = List.of(
            new HelpBotCommand(),
            new ListBotCommand(userRepo),
            new RegisterBotCommand(userRepo),
            new TrackBotCommand(checker, userRepo),
            new UntrackBotCommand(checker, userRepo)
        );
        for (BotCommand cmd : cmds) {
            nameToCmd.put(cmd.name(), cmd);
        }
    }

    public SendMessage handle(Update upd) {
        long id = BotHelper.getChatByUpd(upd);
        String[] msg = upd.message().text().split(" ");
        BotCommand cmd = nameToCmd.get(msg[0].substring(1));
        if (cmd == null) {
            return new SendMessage(id, "Неизвестная команда.");
        }
        return cmd.process(upd);
    }
}
