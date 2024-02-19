package com.yunli.bigdata.producer.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunli.bigdata.producer.sample.service.EventSenderService;

/**
 * @author david
 * @date 2021/4/14 11:17 上午
 */
@RestController
@RequestMapping(value = "/message/test")
public class MessageSenderController {

  private final EventSenderService eventSenderService;

  @Autowired
  public MessageSenderController(EventSenderService eventSenderService) {
    this.eventSenderService = eventSenderService;
  }

  @GetMapping(value = "")
  public ResponseEntity<String> sendMessage() {
    try {
      String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiIzNTViZDE1NC1lZmE0LTQxZTMtOTA1ZC0yNWExODQxOWU4OWEiLCJ1c2VyTmFtZSI6ImxpIiwidXNlck5pY2tuYW1lIjoiZ3JlYXQiLCJvcmdhbml6YXRpb25JZCI6IjQwOWZiODk5LWJjYzgtNGJlOC1hYjdlLTM0MmNmYzkxNGE3ZCIsIm9yZ2FuaXphdGlvbk5hbWUiOiJ6aGFuZ2ppYWtvdSIsIm5hbWVzcGFjZUlkIjoibnNfemhhbmdqaWFrb3VfemhhbmdqaWFrb3UiLCJsb2dpblR5cGUiOiJ1c2VyIiwiZXhwaXJlZCI6IjIwMjQtMDItMjAgMTE6MjM6MTYiLCJleHAiOjE3MDgzOTkzOTZ9.NcK9gFYyCQ5peSK38sT5n15xaDi2IStMiD-OctVnwNHSsppwyeTNP2M1fyzFP3uf0ET7ZtPetvVHjwgai5ASoQ";
      eventSenderService.sendMessage(11L, "rain", "none", token);
      return ResponseEntity.ok().body("success");
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ex.getMessage());
    }
  }
}
