package com.firomsa.MyInboxApp.repo;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.MyInboxApp.entity.EmailListItem;
import com.firomsa.MyInboxApp.entity.EmailListItemKey;

@Repository
public interface EmailListItemRepository extends CassandraRepository<EmailListItem, EmailListItemKey>{
    public List<EmailListItem> findAllByKey_IdAndKey_Label(String id, String label);
}
