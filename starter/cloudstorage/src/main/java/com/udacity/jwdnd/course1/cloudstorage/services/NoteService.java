package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteRepository;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    public Note saveOrUpdateNote(Note note, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        note.setUserid(user.getUserid());

        if (note.getNoteid() == 0) {
            noteRepository.insert(note);
        } else {
            noteRepository.update(note);
        }
        return note;
    }

    public Note findNote(int noteid, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return noteRepository.findById(noteid, user.getUserid());
    }

    public int deleteNote(int noteid) {
        return noteRepository.delete(noteid);
    }

    public List<Note> findAll(int userid) {
        return noteRepository.findAll(userid);
    }
}
