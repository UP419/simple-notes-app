package com.example.notesapp.controller;

import com.example.notesapp.model.Note;
import com.example.notesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/note")
public class NotesController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/{userId}")
    public List<Note> getNotesByUserId(@PathVariable UUID userId) {
        return noteService.getNotesByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<Note> addNoteToUser(@RequestBody AddNoteRequest addNoteRequest) {
        Note note = noteService.addNoteToUser(addNoteRequest.userId(), addNoteRequest.content());
        return ResponseEntity.ok(note);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateNoteById(@RequestBody UpdateNoteRequest updateNoteRequest) {
        noteService.updateUserNote(updateNoteRequest.noteId(), updateNoteRequest.content());
        return ResponseEntity.ok("Note updated successfully");
    }

    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<String> deleteNoteById(@PathVariable UUID noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity.ok("Note deleted successfully");
    }

    public record AddNoteRequest(UUID userId, String content) {
    }

    public record UpdateNoteRequest(UUID noteId, String content) {
    }

}
