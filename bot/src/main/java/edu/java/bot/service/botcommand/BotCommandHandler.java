package edu.java.bot.service.botcommand;

import com.pengrad.telegrambot.model.BotCommand;
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
    private final HashMap<String, BotCmd> nameToCmd = new HashMap<>();

    public BotCommandHandler(UserRepository userRepo, HostResourceChecker checker) {
        List<BotCmd> cmds = List.of(
            new HelpBotCmd(),
            new ListBotCmd(userRepo),
            new RegisterBotCmd(userRepo),
            new TrackBotCmd(checker, userRepo),
            new UntrackBotCmd(checker, userRepo)
        );
        for (BotCmd cmd : cmds) {
            nameToCmd.put(cmd.name(), cmd);
        }
    }

    public SendMessage handle(Update upd) {
        long id = BotHelper.getChatByUpd(upd);
        String[] msg = upd.message().text().split(" ");
        BotCmd cmd = nameToCmd.get(msg[0]);
        if (cmd == null) {
            return new SendMessage(id, "Неизвестная команда.");
        }
        return cmd.process(upd);
    }

    public List<BotCommand> commands() {
        return nameToCmd.values().stream().map(BotCmd::toApiCommand).toList();
    }
}
