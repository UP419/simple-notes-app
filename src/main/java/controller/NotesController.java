package controller;

import model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.NoteService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/note")
public class NotesController {

    @Autowired
    private NoteService noteService;

    @GetMapping("")
    public List<Note> getNotesByUserId(@PathVariable Long userId) {
        return new ArrayList<>();
    }

//    @PostMapping("")
//    public ResponseEntity<String> addNoteToUser(@RequestBody String str) {
//
//    }
//
//    @PostMapping("")
//    public ResponseEntity<String> updateNoteById(@PathVariable Long noteId) {
//
//    }
//
//    @DeleteMapping("")
//    public ResponseEntity<String> deleteNoteById(@PathVariable Long noteId) {
//
//    }

}
