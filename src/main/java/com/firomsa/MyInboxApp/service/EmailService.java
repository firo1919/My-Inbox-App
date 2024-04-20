package com.firomsa.MyInboxApp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.repo.EmailRepository;

@Service
public class EmailService {

    private EmailRepository emailRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public void sendEmail(Email email){
        emailRepository.save(email);
    }
    public Email getEmail(UUID id){
        return emailRepository.findById(id).get();
    }
}
