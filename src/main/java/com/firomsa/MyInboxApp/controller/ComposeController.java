package com.firomsa.MyInboxApp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.service.EmailService;

@Controller
public class ComposeController {
    private EmailService emailService;

    public ComposeController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/compose")
    public String getComposePage(@RequestParam(required = false) String to, @AuthenticationPrincipal OAuth2User principal, Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return "index";
        }
        List<String> uniqueId = splitIds(to);
        model.addAttribute("to",String.join(",",uniqueId));
        
        return "compose_page";
    }

    private List<String> splitIds(String to) {
        if(!StringUtils.hasText(to)){
            return new ArrayList<String>();
        }
        String[] toids = to.split(",");
        List<String> uniqueId = Arrays.asList(toids)
                            .stream()
                            .map(id -> id.strip())
                            .filter(id->StringUtils.hasText(id))
                            .distinct()
                            .collect(Collectors.toList());
        return uniqueId;
    }

    @PostMapping("/send")
    public ModelAndView sendEmail(@RequestBody MultiValueMap<String,String> formdata, @AuthenticationPrincipal OAuth2User principal, Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return new ModelAndView("redirect:/");
        }
        String from = principal.getAttribute("login");
        List<String> to = splitIds(formdata.getFirst("toIds"));
        String subject = formdata.getFirst("subject");
        String body = formdata.getFirst("body");
        Email email = new Email();
        email.setBody(body);
        email.setFrom(from);
        email.setSubject(subject);
        email.setTo(to);
        emailService.sendEmail(email);
        return new ModelAndView("redirect:/");
    }
}
