package edu.java.bot;

import edu.java.bot.service.URLHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLHelperTest {
    @Test
    void tryURLTest() {
        String toTest1 = "amogus";
        String toTest2 = "http://google.com";
        String toTest3 = "isplitcoffeeovermykeyboard..co";
        String toTest4 = "https://www.yandex.ru/";

        URL actual1 = URLHelper.tryURL(toTest1);
        Assertions.assertNull(actual1);

        URL actual2 = URLHelper.tryURL(toTest2);
        Assertions.assertEquals("google.com", actual2.getHost());

        URL actual3 = URLHelper.tryURL(toTest3);
        Assertions.assertNull(actual3);

        URL actual4 = URLHelper.tryURL(toTest4);
        Assertions.assertEquals("www.yandex.ru", actual4.getHost());
    }

    @Test
    void normalizedHostTest() throws URISyntaxException, MalformedURLException {
        URL toTest1 = new URI("https://google.com").toURL();

        String actual1 = URLHelper.getNormalizedHost(toTest1);
        Assertions.assertEquals("google.com", actual1);

        URL toTest2 = new URI("https://www.habr.com").toURL();

        String actual2 = URLHelper.getNormalizedHost(toTest2);
        Assertions.assertEquals("habr.com", actual2);
    }
}
