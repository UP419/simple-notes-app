package service;

import model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getNotesByUserId(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    public void addNoteToUser(Long userId, String content) {
        Note note = new Note(userId, content);
        noteRepository.save(note);
    }

    public void updateUserNote(Long noteId, String content) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + noteId + " not found"));
        note.setContent(content);
        noteRepository.save(note);
    }

    public void deleteNoteById(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id " + noteId + " not found"));
        noteRepository.delete(note);
    }

}
