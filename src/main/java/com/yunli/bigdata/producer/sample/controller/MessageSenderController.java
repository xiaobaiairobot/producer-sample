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
      String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiI5Y2I3YTJiZi02MjEyLTQ1N2QtOThjNC0wNTMzODU3NWU4NGMiLCJ0ZW5hbnRJZCI6IjQyMjZjOGM2LWI1ZTEtNDJiYS1iYmZjLTliOWU4NDY1MTJmYyIsImV4cGlyZWQiOiIyMDIxLTA0LTE0IDIwOjMwOjIzIiwiZXhwIjoxNjE4NDAzNDIzfQ.peChHc1tVbd_QvcnZbM0qSuIn_B_Mmpsydg8fsztxwF0jLj4_klI_3lZRwuACCicoyWptGS1nmRb4QEFBHHgQQ";
      eventSenderService.sendMessage(10L, 22L, "none", token);
      return ResponseEntity.ok().body("success");
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ex.getMessage());
    }
  }
}
