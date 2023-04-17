package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    public int insertFile(MultipartFile multipartFile, Principal principal) throws IOException {
        File file = new File();
        file.setContenttype(multipartFile.getContentType());
        file.setFilename(multipartFile.getOriginalFilename());
        file.setFiledata(multipartFile.getBytes());
        file.setUserid(userRepository.findByUsername(principal.getName()).getUserid());
        file.setFilesize(String.valueOf(multipartFile.getSize()));

        return fileRepository.insert(file);
    }

    public int deleteFile(int fileId) {
        return fileRepository.deleteFile(fileId);
    }

    public File findFile(int fileId) {
        return fileRepository.findById(fileId);
    }


    public List<File> findAll(int userid) {
        return fileRepository.findAll(userid).stream().map(file -> {
            file.setFiledata(null);
            return file;
        }).collect(Collectors.toList());
    }
}
