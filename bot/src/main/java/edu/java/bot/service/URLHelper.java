package edu.java.bot.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLHelper {
    private URLHelper() {

    }

    static final String WWW_PART = "www.";

    public static String getNormalizedHost(URL url) {
        String res = url.getHost();
        if (res.startsWith(WWW_PART)) {
            res = res.substring(WWW_PART.length());
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
