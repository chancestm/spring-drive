package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppUtil.extractValidationErrors;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdateNote(@Valid @RequestBody Note note, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(412).body(extractValidationErrors(bindingResult));
        }

        return ResponseEntity.ok(noteService.saveOrUpdateNote(note, principal));
    }

    @GetMapping("/find/{noteid}")
    public ResponseEntity<Note> getNote(@PathVariable int noteid, Principal principal) {
        return ResponseEntity.ok(noteService.findNote(noteid, principal));
    }

    @GetMapping("/delete/{noteid}")
    public ResponseEntity<String> deleteNote(@PathVariable int noteid) {
        noteService.deleteNote(noteid);
        return ResponseEntity.ok("Successfully deleted note");
    }
}
