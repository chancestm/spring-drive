package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialRepository;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private EncryptionService encryptionService;

    public Credential saveOrUpdateCredential(Credential credential, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        credential.setUserid(user.getUserid());

        if (credential.getCredentialid() == 0) {

            credential.setKey(AppUtil.generateAesKey());
            credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));

            credentialRepository.insert(credential);
        } else {
            Credential dbCredential = credentialRepository.findById(credential.getCredentialid(), user.getUserid());

            credential.setPassword(encryptionService.encryptValue(credential.getPassword(), dbCredential.getKey()));
            credentialRepository.update(credential);
        }
        return credential;
    }

    public Credential findCredential(int credentialid, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Credential credential = credentialRepository.findById(credentialid, user.getUserid());
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));

        return credential;
    }

    public int deleteCredential(int credentialid) {
        return credentialRepository.delete(credentialid);
    }

    public List<Credential> findAll(int userid) {
        return credentialRepository.findAll(userid).stream().map(credential -> {
            credential.setKey(null);
            return credential;
        }).collect(Collectors.toList());
    }

}
