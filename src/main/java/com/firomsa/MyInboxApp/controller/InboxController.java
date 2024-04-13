package com.firomsa.MyInboxApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.firomsa.MyInboxApp.entity.Folder;
import com.firomsa.MyInboxApp.service.FolderService;

import jakarta.annotation.PostConstruct;

@Controller
public class InboxController {


	FolderService folderService;

	@Autowired
	public InboxController(FolderService folderService) {
		this.folderService = folderService;
	}

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal,Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return "index";
        }
        String userid = principal.getAttribute("login");
        List<Folder> folders = folderService.getFolders(userid);
        model.addAttribute("userfolders", folders);
        return "inbox_page";
    }

    @PostConstruct
	public void init(){
		folderService.save(new Folder("firo1919", "Inbox", "blue"));
		folderService.save(new Folder("firo1919", "Sent", "red"));
		folderService.save(new Folder("firo1919", "Important", "orange"));
	}
}
