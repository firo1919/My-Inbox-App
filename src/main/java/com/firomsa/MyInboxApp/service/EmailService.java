package com.firomsa.MyInboxApp.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.entity.EmailListItem;
import com.firomsa.MyInboxApp.entity.EmailListItemKey;
import com.firomsa.MyInboxApp.repo.EmailListItemRepository;
import com.firomsa.MyInboxApp.repo.EmailRepository;

@Service
public class EmailService {

    private EmailRepository emailRepository;
    private EmailListItemRepository emailListItemRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public void sendEmail(Email email){
        email.setId(Uuids.timeBased());
        emailRepository.save(email);
        email.getTo().forEach(id -> {
            EmailListItem emailListItem = createEmailListItem(email, id, "Inbox");
            emailListItemRepository.save(emailListItem);
        });
        emailListItemRepository.save(createEmailListItem(email, email.getFrom(), "Sent Messages"));
    }

    private EmailListItem createEmailListItem(Email email, String id, String folder) {
        EmailListItemKey emailListItemKey = new EmailListItemKey();
        emailListItemKey.setId(id);
        emailListItemKey.setLabel(folder);
        emailListItemKey.setTimeUuid(email.getId());
        EmailListItem emailListItem = new EmailListItem();
        emailListItem.setKey(emailListItemKey);
        emailListItem.setRead(false);
        emailListItem.setSubject(email.getSubject());
        emailListItem.setTo(email.getTo());
        return emailListItem;
    }
    public Email getEmail(UUID id){
        Optional<Email> email =  emailRepository.findById(id);
        return email.isEmpty()?null:email.get();
    }
}
