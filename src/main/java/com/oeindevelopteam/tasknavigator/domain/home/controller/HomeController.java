package com.oeindevelopteam.tasknavigator.domain.home.controller;

import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

  @GetMapping()
  public String homePage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    System.out.println(userDetails.getUser());
    return "index";
  }

  @GetMapping("/newBoard")
  public String createBoardPage() {
    return "create-board";
  }

  // TODO: 카드 번호에 따라 다른 상세 페이지를 보여주는 것이 필요
//  @GetMapping("/cards/{cardId}")
  @GetMapping("/card")
  public String cardDetailsPage() {
    return "card-details";
  }

  @GetMapping("/cards")
  public String cardsPage() {
    return "cards";
  }

  // TODO: 카드 번호에 따라 다른 수정 페이지를 보여주는 것이 필요
//  @GetMapping("/cards/edit/{cardId}")
  @GetMapping("/card/edit")
  public String cardEditPage() {
    return "edit-card";
  }
}
