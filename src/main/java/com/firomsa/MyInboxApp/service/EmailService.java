package com.firomsa.MyInboxApp.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.repo.EmailRepository;
@Service
public class EmailService {

    private EmailRepository emailRepository;
    private EmailListItemService emailListItemService;

    public EmailService(EmailRepository emailRepository, EmailListItemService emailListItemService) {
        this.emailRepository = emailRepository;
        this.emailListItemService = emailListItemService;
    }

    public void sendEmail(Email email){
        email.setId(Uuids.timeBased());
        emailRepository.save(email);
        emailListItemService.save(email);
    }
   
    public Email getEmail(UUID id){
        Optional<Email> email =  emailRepository.findById(id);
        return email.isEmpty()?null:email.get();
    }
}
