package com.firomsa.MyInboxApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.entity.EmailListItem;
import com.firomsa.MyInboxApp.entity.EmailListItemKey;
import com.firomsa.MyInboxApp.entity.UnreadEmailStats;
import com.firomsa.MyInboxApp.repo.EmailRepository;
import com.firomsa.MyInboxApp.repo.UnreadEmailStatsRepository;

@Service
public class EmailService {

    private EmailRepository emailRepository;
    private EmailListItemService emailListItemService;
    private UnreadEmailStatsRepository unreadEmailStatsRepository;

    public EmailService(EmailRepository emailRepository, EmailListItemService emailListItemService, UnreadEmailStatsRepository unreadEmailStatsRepository) {
        this.emailRepository = emailRepository;
        this.emailListItemService = emailListItemService;
        this.unreadEmailStatsRepository = unreadEmailStatsRepository;
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

    public boolean getUnreadStatus(String userId, String folder, Email email) {
        return emailListItemService.getEmailListItem(new EmailListItemKey(userId, folder, email.getId())).isRead();
    }

    public void setUnreadStatus(boolean b,String userId, String folder, Email email) {
        EmailListItem emailListItem = emailListItemService.getEmailListItem(new EmailListItemKey(userId, folder, email.getId()));
        emailListItem.setRead(b);
        emailListItemService.update(emailListItem);
    }
    public int getFolderUnreadCount(String userId, String folder) {
        int count = 0;
        if(unreadEmailStatsRepository!=null){
            List<UnreadEmailStats> stats = unreadEmailStatsRepository.findAllById(userId);
            count = stats.stream().filter(n->(n.getLabel().equals(folder))).map(n->n.getUnreadCount()).reduce(0,Integer::sum);
            return count;
        }
        return count;
    }
}
