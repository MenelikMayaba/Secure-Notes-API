package aCompany.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    User user;
    String title;
    String content;

    public Note(){}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
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
}
