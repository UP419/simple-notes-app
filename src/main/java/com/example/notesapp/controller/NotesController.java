package com.example.notesapp.controller;

import com.example.notesapp.model.Note;
import com.example.notesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/note")
public class NotesController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/{userId}")
    public List<Note> getNotesByUserId(@PathVariable Long userId) {
        return noteService.getNotesByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNoteToUser(@RequestBody AddNoteRequest addNoteRequest) {
        noteService.addNoteToUser(addNoteRequest.userId(), addNoteRequest.content());
        return ResponseEntity.ok("Note added successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateNoteById(@RequestBody UpdateNoteRequest updateNoteRequest) {
        noteService.updateUserNote(updateNoteRequest.noteId(), updateNoteRequest.content());
        return ResponseEntity.ok("Note updated successfully");
    }

    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<String> deleteNoteById(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity.ok("Note deleted successfully");
    }

    public record AddNoteRequest(Long userId, String content) {
    }

    public record UpdateNoteRequest(Long noteId, String content) {
    }

}
