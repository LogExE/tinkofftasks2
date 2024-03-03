package edu.java.bot.controller;

import edu.java.bot.controller.dto.LinkUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RecieveUpdateController {
    @PostMapping("/updates")
    public ResponseEntity<Void> notifyUpdate(@RequestBody LinkUpdate req) {
        log.info("update notification " + req);
        return ResponseEntity.ok().build();
    }
}
