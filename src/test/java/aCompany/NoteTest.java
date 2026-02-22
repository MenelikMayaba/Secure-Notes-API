package com.example.securitydemo.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NoteTest {

    @Test
    void shouldSetAndGetTitle() {
        Note note = new Note();
        note.setTitle("Test Note");
        assertEquals("Test Note", note.getTitle());
    }

    @Test
    void shouldSetAndGetContent() {
        Note note = new Note();
        note.setContent("This is a test content");
        assertEquals("This is a test content", note.getContent());
    }

    @Test
    void shouldSetAndGetUser() {
        Note note = new Note();
        User user = new User();
        user.setUsername("yaba");

        note.setUser(user);
        assertEquals("yaba", note.getUser().getUsername());
    }

    @Test
    void noteShouldNotBeNullByDefault() {
        Note note = new Note();
        assertNotNull(note); // sanity check
    }
}