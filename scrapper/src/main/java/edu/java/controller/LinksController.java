package edu.java.controller;

import edu.java.controller.dto.AddLinkRequest;
import edu.java.controller.dto.LinkResponse;
import edu.java.controller.dto.ListLinksResponse;
import edu.java.controller.dto.RemoveLinkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/links")
public class LinksController {
    private static final String TG_CHAT_HEADER = "Tg-Chat-Id";

    @GetMapping
    public ResponseEntity<ListLinksResponse> listLinks(@RequestHeader(TG_CHAT_HEADER) long id) {
        log.info("get links chat id " + id);
        return ResponseEntity.ok(new ListLinksResponse(List.of(), 0));
    }

    @PostMapping
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader(TG_CHAT_HEADER) long id,
        @RequestBody AddLinkRequest req
    ) {
        log.info("add link, chat id " + id + ": " + req);
        return ResponseEntity.ok(new LinkResponse(id, req.link()));
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> removeLink(
        @RequestHeader(TG_CHAT_HEADER) long id, @RequestBody
    RemoveLinkRequest req
    ) {
        log.info("remove link, chat id " + id + ": " + req);
        return ResponseEntity.ok(new LinkResponse(id, req.link()));
    }
}
