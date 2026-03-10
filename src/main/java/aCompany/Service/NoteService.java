package aCompany.Service;

import aCompany.Repository.NoteRepository;
import aCompany.entity.Note;
import aCompany.entity.User;
import aCompany.entity.Roles;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Note> getNotesByUsername(String username) {
        return noteRepository.findAll().stream()
                .filter(note -> username.equals(note.getUsername()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNoteById(Long id, String username, Roles role) {
        Optional<Note> noteOpt = noteRepository.findById(id);
        if (noteOpt.isPresent()) {
            Note note = noteOpt.get();
            if (role == Roles.ADMIN || username.equals(note.getUsername())) {
                noteRepository.delete(note);
            } else {
                throw new RuntimeException("Unauthorized");
            }
        } else {
            throw new RuntimeException("Note not found");
        }
    }
}
