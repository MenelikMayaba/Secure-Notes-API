package aCompany.Controller;


import aCompany.Service.NoteService;
import aCompany.Service.UserService;
import aCompany.entity.Note;
import aCompany.entity.Roles;
import aCompany.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> createNote(@RequestBody Note note, Authentication authentication) {
        if (note == null || note.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note needs a title and cannot be null");

        }

        String userName = authentication.getName();

        Optional<User> user = userService.findByUsername(userName);

        try {
            user.get().setUsername(userName);
            noteService.createNoteForUser(note, user.orElse(null));

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to create note", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes(Authentication authentication) {

        String userName = authentication.getName();

        List<Note> notes = noteService.getNotesByUsername(userName);
        try {
            for(Note note : notes){
                System.out.println(note);

            }

            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to get notes from", e);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNote(@RequestParam long l, Authentication authentication) {
        String userName = authentication.getName();

        noteService.deleteNoteById(l, userName, Roles.USER);

        return ResponseEntity.ok().build();

    }
}
