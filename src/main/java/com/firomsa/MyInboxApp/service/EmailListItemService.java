package com.firomsa.MyInboxApp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firomsa.MyInboxApp.entity.Email;
import com.firomsa.MyInboxApp.entity.EmailListItem;
import com.firomsa.MyInboxApp.entity.EmailListItemKey;
import com.firomsa.MyInboxApp.repo.EmailListItemRepository;
import com.firomsa.MyInboxApp.repo.UnreadEmailStatsRepository;

@Service
public class EmailListItemService {
    private EmailListItemRepository emailListItemRepository;
    private UnreadEmailStatsRepository unreadEmailStatsRepository;

    public EmailListItemService(EmailListItemRepository emailListItemRepository, UnreadEmailStatsRepository unreadEmailStatsRepository) {
        this.emailListItemRepository = emailListItemRepository;
        this.unreadEmailStatsRepository = unreadEmailStatsRepository;
    }

    public List<EmailListItem> getEmailListItems(String folder, String id){
        return emailListItemRepository.findAllByKey_IdAndKey_Label(id, folder);
    }
    public EmailListItem getEmailListItem(EmailListItemKey key){
        return emailListItemRepository.findById(key).get();
    }
    public void save(Email email){
        email.getTo().forEach(id -> {
            EmailListItem emailListItem = createEmailListItem(email, id, "Inbox");
            emailListItemRepository.save(emailListItem);
            unreadEmailStatsRepository.incrementUnreadCount(id, "Inbox");
        });
        emailListItemRepository.save(createEmailListItem(email, email.getFrom(), "Sent Messages"));
        unreadEmailStatsRepository.incrementUnreadCount(email.getFrom(), "Sent Messages");
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

    @Transactional
    public void update(EmailListItem emailListItem){
        emailListItemRepository.save(emailListItem);
    }

}
