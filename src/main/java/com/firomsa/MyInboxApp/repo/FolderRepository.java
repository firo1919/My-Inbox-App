package com.firomsa.MyInboxApp.repo;

import com.firomsa.MyInboxApp.entity.Folder;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CassandraRepository<Folder, String>{
    public List<Folder> findAllById(String id);
}
