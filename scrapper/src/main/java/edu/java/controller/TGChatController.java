package edu.java.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tg-chat/{id}")
public class TGChatController {
    @PostMapping
    public ResponseEntity<Void> registerChat(@PathVariable long id) {
        log.info("create chat " + id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteChat(@PathVariable long id) {
        log.info("delete chat " + id);
        return ResponseEntity.ok().build();
    }
}
