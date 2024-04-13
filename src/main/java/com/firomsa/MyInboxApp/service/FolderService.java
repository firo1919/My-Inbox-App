package com.firomsa.MyInboxApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firomsa.MyInboxApp.entity.Folder;
import com.firomsa.MyInboxApp.repo.FolderRepository;

@Service
public class FolderService {
    private FolderRepository folderRepository;
    
    @Autowired
    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public void save(Folder folder){
        folderRepository.save(folder);
    }
    public List<Folder> getFolders(String id){
        List<Folder> folders = folderRepository.findAllById(id);
        return folders;
    }
}
