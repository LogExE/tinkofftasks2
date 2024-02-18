package edu.java.bot.service.infores;

import edu.java.bot.service.URLHelper;
import java.net.URL;
import org.springframework.stereotype.Component;

@Component
public class HostResourceChecker {
    private final InfoResource toCheck;

    public HostResourceChecker() {
        toCheck = new GitHubResource(new StackOverflowResource(new DummyResource()));
    }

    public boolean isLinkSupported(URL url) {

        return toCheck.isHostRelated(URLHelper.getNormalizedHost(url));
    }
}
