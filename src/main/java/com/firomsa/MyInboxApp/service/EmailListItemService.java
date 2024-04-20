package com.firomsa.MyInboxApp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.firomsa.MyInboxApp.entity.EmailListItem;
import com.firomsa.MyInboxApp.entity.EmailListItemKey;
import com.firomsa.MyInboxApp.repo.EmailListItemRepository;

import jakarta.annotation.PostConstruct;

@Service
public class EmailListItemService {

    private EmailListItemRepository emailListItemRepository;

    @Autowired
    public EmailListItemService(EmailListItemRepository emailListItemRepository) {
        this.emailListItemRepository = emailListItemRepository;
    }

    public void save(EmailListItem emailListItem){
        emailListItemRepository.save(emailListItem);
    }
    public List<EmailListItem> findInboxEmails(String id){
        return emailListItemRepository.findAllByKey_IdAndKey_Label(id, "Inbox");
    }

    @PostConstruct
    public void init(){
        for (int i = 0; i < 3; i++) {
            EmailListItemKey  key = new EmailListItemKey();
            key.setId("firo1919");
            key.setLabel("Inbox");
            key.setTimeUuid(Uuids.timeBased());

            EmailListItem email = new EmailListItem();
            email.setKey(key);
            email.setTo(Arrays.asList("firo1919"));
            email.setRead(false);
            email.setSubject("Subject : "+i);
            this.save(email);
        }
	}
}
