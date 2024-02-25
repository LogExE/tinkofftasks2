package edu.java.bot;

import edu.java.bot.repository.UserRepository;
import edu.java.bot.service.botcommand.ListBotCmd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;

public class BotTests {
    @Test
    void testTrackListFmt() {
        UserRepository userRepo = new UserRepository();
        userRepo.registerUser(42);
        userRepo.addTrack(42, "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");

        Set<String> tracked = userRepo.linksByUser(42);

        String actualFmt = ListBotCmd.fmtTracked(tracked);
        Assertions.assertEquals("Список подписок: \n" +
            " - https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c", actualFmt);
    }

    @Test
    void testEmptyTracked() {
        UserRepository userRepo = new UserRepository();
        userRepo.registerUser(36);

        Set<String> tracked = userRepo.linksByUser(36);

        String actualFmt = ListBotCmd.fmtTracked(tracked);
        Assertions.assertEquals("Список подписок: пусто", actualFmt);
    }
}
