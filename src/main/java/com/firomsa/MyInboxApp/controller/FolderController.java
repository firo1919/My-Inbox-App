package com.firomsa.MyInboxApp.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.firomsa.MyInboxApp.entity.EmailListItem;
import com.firomsa.MyInboxApp.entity.Folder;
import com.firomsa.MyInboxApp.service.EmailListItemService;
import com.firomsa.MyInboxApp.service.FolderService;

@Controller
public class FolderController {


	FolderService folderService;
    EmailListItemService emailListItemService;

	@Autowired
	public FolderController(FolderService folderService, EmailListItemService emailListItemService) {
        this.emailListItemService = emailListItemService;
	}

    @GetMapping("/folder")
    public String home(@RequestParam String folder, @AuthenticationPrincipal OAuth2User principal,Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))){
            return "index";
        }
        String userid = principal.getAttribute("login");

        PrettyTime p = new PrettyTime();
        //fetch messages
        List<EmailListItem> inboxEmails = emailListItemService.findInboxEmails(userid);
        inboxEmails.forEach(emailItem->{
            UUID timeuuid =  emailItem.getKey().getTimeUuid();
            Date emailDateTime = new Date(Uuids.unixTimestamp(timeuuid));
            emailItem.setAgoTimeString(p.format(emailDateTime));
        });

        model.addAttribute("inboxlist", inboxEmails);
        model.addAttribute("folder",folder);

        return "folder";
    }
}
