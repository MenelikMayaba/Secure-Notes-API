package com.example.securitydemo.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.example.securitydemo.entity.Note;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.NoteRepository;

class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNoteShouldAttachUser() {
        User user = new User();
        user.setUsername("yaba");

        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Content");

        noteService.createNoteForUser(note, user);

        Mockito.verify(noteRepository).save(Mockito.argThat(n ->
                n.getUser().getUsername().equals("yaba") &&
                        n.getTitle().equals("Test Note")
        ));
    }
}