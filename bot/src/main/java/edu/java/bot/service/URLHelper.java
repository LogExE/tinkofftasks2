package edu.java.bot.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLHelper {
    private URLHelper() {

    }

    @SuppressWarnings("MagicNumber")
    public static String getNormalizedHost(URL url) {
        String res = url.getHost();
        if (res.startsWith("www.")) {
            res = res.substring(4);
        }
        return res;
    }

    public static URL tryURL(String str) {
        try {
            return new URI(str).toURL();
        } catch (MalformedURLException | URISyntaxException | IllegalArgumentException e) {
            return null;
        }
    }
}
