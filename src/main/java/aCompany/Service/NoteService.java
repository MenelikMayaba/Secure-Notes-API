package aCompany.Service;

import aCompany.Repository.NoteRepository;
import aCompany.entity.Note;
import aCompany.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private String title;
    private String content;
    private User user;
    private NoteRepository noteRepository;

    public NoteService() {
    }

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transactional
    public void createNoteForUser(Note note, User user){
        note.setUsername(user.getUsername());
        noteRepository.save(note);
    }
}
