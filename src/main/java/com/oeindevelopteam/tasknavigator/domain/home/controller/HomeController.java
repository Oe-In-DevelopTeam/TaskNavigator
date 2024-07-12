package com.oeindevelopteam.tasknavigator.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/home")
  public String homePage() {
    return "index";
  }

  @GetMapping("/home/newBoard")
  public String createBoardPage() {
    return "create-board";
  }
}
