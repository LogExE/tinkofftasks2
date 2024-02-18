package edu.java.bot;

import edu.java.bot.service.infores.HostResourceChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class InfoResourceTest {
    @Test
    void testStackoverflow() throws URISyntaxException, MalformedURLException {
        HostResourceChecker checker = new HostResourceChecker();

        URL url = new URI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c").toURL();

        boolean actual = checker.isLinkSupported(url);
        Assertions.assertTrue(actual);
    }

    @Test
    void testGithub() throws URISyntaxException, MalformedURLException {
        HostResourceChecker checker = new HostResourceChecker();

        URL url = new URI("https://github.com/LogExE/tinkofftasks2").toURL();

        boolean actual = checker.isLinkSupported(url);
        Assertions.assertTrue(actual);
    }

    @Test
    void testYoutube() throws URISyntaxException, MalformedURLException {
        HostResourceChecker checker = new HostResourceChecker();

        URL url = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURL();

        boolean actual = checker.isLinkSupported(url);
        Assertions.assertFalse(actual);
    }
}
