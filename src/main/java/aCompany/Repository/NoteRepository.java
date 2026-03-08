package aCompany.Repository;

import aCompany.entity.Note;
import aCompany.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
