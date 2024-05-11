package com.firomsa.MyInboxApp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firomsa.MyInboxApp.entity.Folder;
import com.firomsa.MyInboxApp.repo.FolderRepository;

import jakarta.annotation.PostConstruct;

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
    public List<Folder> getFolders(String userId){
        List<Folder> folders = folderRepository.findAllById(userId);
        return folders;
    }
    public List<Folder> fetchDefaultFolders(String userId){
        return Arrays.asList(
            new Folder(userId, "Inbox", "white"),
            new Folder("firo1919", "Sent Messages", "green"),
            new Folder(userId, "Important", "red")
            );
    }
    @PostConstruct
    public void init(){
		this.save(new Folder("firo1919", "Inbox", "blue"));
		this.save(new Folder("firo1919", "Sent", "red"));
		this.save(new Folder("firo1919", "Important", "orange"));
	}
}
