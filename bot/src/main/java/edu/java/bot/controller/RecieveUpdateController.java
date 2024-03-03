package edu.java.bot.controller;

import edu.java.bot.controller.dto.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecieveUpdateController {
    @PostMapping("/updates")
    public ResponseEntity<Void> notifyUpdate(@RequestBody LinkUpdate req) {
        return ResponseEntity.ok().build();
    }
}
