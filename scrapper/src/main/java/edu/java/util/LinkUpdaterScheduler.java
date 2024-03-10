package edu.java.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled")
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("LinkUpdateScheduler.update() has been launched");
    }
}
