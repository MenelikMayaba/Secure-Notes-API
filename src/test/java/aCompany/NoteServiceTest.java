package aCompany;

import aCompany.Service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import aCompany.entity.Note;
import aCompany.entity.User;
import aCompany.Repository.NoteRepository;

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
                n.getUsername().equals("yaba") &&
                        n.getTitle().equals("Test Note")
        ));
    }
}