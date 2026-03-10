package aCompany;

import aCompany.Controller.NoteController;
import aCompany.Service.NoteService;
import aCompany.Service.UserService;
import aCompany.entity.Note;
import aCompany.entity.Roles;
import aCompany.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteControllerTest {

    @InjectMocks
    private NoteController noteController;

    @Mock
    private NoteService noteService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNoteShouldCallService() {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Content");

        Mockito.when(authentication.getName()).thenReturn("yaba");

        ResponseEntity<Void> response = noteController.createNote(note, authentication);

        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(noteService).createNoteForUser(Mockito.any(Note.class), Mockito.any(User.class));
    }

    @Test
    void getNotesShouldReturnUserNotes() {
        Mockito.when(authentication.getName()).thenReturn("yaba");

        Note note1 = new Note();
        note1.setTitle("Note 1");
        Note note2 = new Note();
        note2.setTitle("Note 2");
        List<Note> notes = Arrays.asList(note1, note2);

        Mockito.when(noteService.getNotesByUsername("yaba")).thenReturn(notes);

        ResponseEntity<List<Note>> response = noteController.getNotes(authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Note 1", response.getBody().get(0).getTitle());
    }

    @Test
    void deleteNoteShouldCallServiceForUser() {
        Mockito.when(authentication.getName()).thenReturn("yaba");

        ResponseEntity<Void> response = noteController.deleteNote(1L, authentication);

        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(noteService).deleteNoteById(1L, "yaba", Roles.USER);
    }
}
