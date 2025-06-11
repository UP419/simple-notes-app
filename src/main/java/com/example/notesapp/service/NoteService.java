package com.example.notesapp.service;

import com.example.notesapp.exception.NoteNotFoundException;
import com.example.notesapp.model.Note;
import com.example.notesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getNotesByUserId(UUID userId) {
        return noteRepository.findByUserId(userId);
    }

    public Note addNoteToUser(UUID userId, String content) {
        Note note = new Note(userId, content);
        noteRepository.save(note);
        return note;
    }

    public void updateUserNote(UUID noteId, String content) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
        note.setContent(content);
        noteRepository.save(note);
    }

    public void deleteNoteById(UUID noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
        noteRepository.delete(note);
    }

}
