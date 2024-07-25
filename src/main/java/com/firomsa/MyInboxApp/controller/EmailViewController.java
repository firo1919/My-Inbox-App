package com.firomsa.MyInboxApp.controller;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.repo.UnreadEmailStatsRepository;
import com.firomsa.MyInboxApp.service.EmailService;

@Controller
public class EmailViewController {

    EmailService emailService;
    UnreadEmailStatsRepository unreadEmailStatsRepository;

    public EmailViewController(EmailService emailService, UnreadEmailStatsRepository unreadEmailStatsRepository) {
        this.emailService = emailService;
        this.unreadEmailStatsRepository = unreadEmailStatsRepository;
    }

    @GetMapping("emails/{id}/{folder}")
    public String emailView(@PathVariable UUID id, @PathVariable String folder, @AuthenticationPrincipal OAuth2User principal,Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return "index";
        }

        String userId = principal.getAttribute("login");
        Email email = emailService.getEmail(id);
        String toIds = String.join(", ", email.getTo());
        boolean read = emailService.getUnreadStatus(userId,folder,email);
        model.addAttribute("email", email);
        model.addAttribute("toIds", toIds);
        if(!read){
            unreadEmailStatsRepository.decrementUnreadCount(userId, folder);
            emailService.setUnreadStatus(true,userId,folder,email);
        }
        return "emailview";
    }
}
