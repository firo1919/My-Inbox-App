package com.firomsa.MyInboxApp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComposeController {

    @GetMapping("/compose")
    public String getComposePage(@AuthenticationPrincipal OAuth2User principal, Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return "index";
        }

        return "compose_page";
    }
}
