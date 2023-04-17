package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class CommonController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @GetMapping("home")
    public String viewHome(ModelMap modelMap, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        modelMap.addAttribute("files", fileService.findAll(user.getUserid()));
        modelMap.addAttribute("notes", noteService.findAll((user.getUserid())));
        modelMap.addAttribute("credentials", credentialService.findAll(user.getUserid()));

        return "home";
    }

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("result")
    public String getResultView() {
        return "result";
    }

}
