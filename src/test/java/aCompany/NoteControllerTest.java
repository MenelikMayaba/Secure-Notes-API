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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        User user = new User();
        user.setUsername("yaba");

        Mockito.when(authentication.getName()).thenReturn("yaba");
        Mockito.when(userService.findByUsername("yaba")).thenReturn(Optional.of(user));

        ResponseEntity<Void> response = noteController.createNote(note, authentication);

        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(noteService).createNoteForUser(Mockito.any(Note.class), Mockito.any(User.class));
    }

    @Test
    void createNoteShouldThrowBadRequestWhenNoteIsNull() {
        Mockito.when(authentication.getName()).thenReturn("yaba");

        assertThrows(ResponseStatusException.class, () -> {
            noteController.createNote(null, authentication);
        });
    }

    @Test
    void createNoteShouldThrowBadRequestWhenTitleIsNull() {
        Note note = new Note();
        note.setContent("Content");

        Mockito.when(authentication.getName()).thenReturn("yaba");

        assertThrows(ResponseStatusException.class, () -> {
            noteController.createNote(note, authentication);
        });
    }

    @Test
    void createNoteShouldHandleServiceException() {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Content");

        User user = new User();
        user.setUsername("yaba");

        Mockito.when(authentication.getName()).thenReturn("yaba");
        Mockito.when(userService.findByUsername("yaba")).thenReturn(Optional.of(user));
        Mockito.doThrow(new RuntimeException("Service error")).when(noteService).createNoteForUser(Mockito.any(Note.class), Mockito.any(User.class));

        assertThrows(ResponseStatusException.class, () -> {
            noteController.createNote(note, authentication);
        });
    }

    @Test
    void getNotesShouldReturnUserNotes() {
        Mockito.when(authentication.getName()).thenReturn("yaba");

        Note note1 = new Note();
        note1.setTitle("Note 1");
        note1.setContent("Content 1");
        Note note2 = new Note();
        note2.setTitle("Note 2");
        note2.setContent("Content 2");
        List<Note> notes = Arrays.asList(note1, note2);

        Mockito.when(noteService.getNotesByUsername("yaba")).thenReturn(notes);

        ResponseEntity<List<Note>> response = noteController.getNotes(authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Note 1", response.getBody().get(0).getTitle());
        assertEquals("Content 1", response.getBody().get(0).getContent());
        assertEquals("Note 2", response.getBody().get(1).getTitle());
        assertEquals("Content 2", response.getBody().get(1).getContent());
    }

    @Test
    void getNotesShouldReturnEmptyListWhenNoNotes() {
        Mockito.when(authentication.getName()).thenReturn("yaba");
        Mockito.when(noteService.getNotesByUsername("yaba")).thenReturn(Arrays.asList());

        ResponseEntity<List<Note>> response = noteController.getNotes(authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getNotesShouldHandleServiceException() {
        Mockito.when(authentication.getName()).thenReturn("yaba");
        Mockito.when(noteService.getNotesByUsername("yaba")).thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, () -> {
            noteController.getNotes(authentication);
        });
    }

    @Test
    void deleteNoteShouldCallServiceForUser() {
        Mockito.when(authentication.getName()).thenReturn("yaba");

        ResponseEntity<User> response = noteController.deleteNote(1L, authentication);

        assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(noteService).deleteNoteById(1L, "yaba", Roles.USER);
    }

    @Test
    void deleteNoteShouldCallServiceForDifferentNoteIds() {
        Mockito.when(authentication.getName()).thenReturn("yaba");

        ResponseEntity<User> response1 = noteController.deleteNote(1L, authentication);
        ResponseEntity<User> response2 = noteController.deleteNote(999L, authentication);

        assertEquals(200, response1.getStatusCodeValue());
        assertEquals(200, response2.getStatusCodeValue());
        Mockito.verify(noteService).deleteNoteById(1L, "yaba", Roles.USER);
        Mockito.verify(noteService).deleteNoteById(999L, "yaba", Roles.USER);
    }

    @Test
    void deleteNoteShouldHandleServiceException() {
        Mockito.when(authentication.getName()).thenReturn("yaba");
        Mockito.doThrow(new RuntimeException("Note not found")).when(noteService).deleteNoteById(Mockito.anyLong(), Mockito.anyString(), Mockito.any(Roles.class));

        assertThrows(RuntimeException.class, () -> {
            noteController.deleteNote(1L, authentication);
        });
    }
}
