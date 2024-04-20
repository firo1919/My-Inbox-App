package com.firomsa.MyInboxApp.repo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.MyInboxApp.entity.Email;

@Repository
public interface EmailRepository extends CassandraRepository<Email, UUID>{

}
