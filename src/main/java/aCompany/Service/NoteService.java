package aCompany.Service;

import aCompany.entity.User;

public class NoteService {

    private String title;
    private String content;
    private User user;

    public NoteService() {
    }

    public NoteService(String title, User user, String content) {
        this.title = title;
        this.user = user;
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
