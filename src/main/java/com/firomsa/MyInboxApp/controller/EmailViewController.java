package com.firomsa.MyInboxApp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.service.EmailService;

@Controller
public class EmailViewController {

    EmailService emailService;

    @Autowired
    public EmailViewController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("emails/{id}")
    public String emailView(@PathVariable UUID id, @AuthenticationPrincipal OAuth2User principal,Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return "index";
        }
        Email email = emailService.getEmail(id);
        if(email==null){
            return "inbox_page";
        }
        String toIds = String.join(", ", email.getTo());
        model.addAttribute("email", email);
        model.addAttribute("toIds", toIds);
        return "emailview";

    }
}
