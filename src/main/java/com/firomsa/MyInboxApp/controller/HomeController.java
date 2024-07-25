package com.firomsa.MyInboxApp.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.firomsa.MyInboxApp.entity.Folder;
import com.firomsa.MyInboxApp.service.FolderService;

@Controller
public class HomeController {
    
    FolderService folderService;

	public HomeController(FolderService folderService) {
		this.folderService = folderService;
	}
    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal,Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return "index";
        }
        String userid = principal.getAttribute("login");
        //fetch folders
        List<Folder> folders = folderService.getFolders(userid);
        model.addAttribute("defaultfolders", folders);
        model.addAttribute("user",userid);
        return "home";
    }
}
