package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    public int saveUser(User user) {
        user.setSalt(AppUtil.generateSalt());
        user.setPassword(hashService.getHashedValue(user.getPassword(), user.getSalt()));

        return userRepository.insert(user);
    }

}
