package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppUtil.extractValidationErrors;

@RestController
@RequestMapping("credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdateCredential(@Valid @RequestBody Credential credential, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(412).body(extractValidationErrors(bindingResult));
        }

        return ResponseEntity.ok(credentialService.saveOrUpdateCredential(credential, principal));
    }

    @GetMapping("/find/{credentialid}")
    public ResponseEntity<Credential> getCredential(@PathVariable int credentialid, Principal principal) {
        return ResponseEntity.ok(credentialService.findCredential(credentialid, principal));
    }

    @GetMapping("/delete/{credentialid}")
    public ResponseEntity<String> deleteCredential(@PathVariable int credentialid) {
        credentialService.deleteCredential(credentialid);
        return ResponseEntity.ok("Successfully deleted note");
    }
}
