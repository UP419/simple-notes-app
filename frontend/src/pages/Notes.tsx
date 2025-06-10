import { useEffect, useState } from 'react';
import { addNote, deleteNote, getNotes, updateNote } from '../services/api';
import { useNavigate } from 'react-router-dom';
import '../App.css';


type Note = {
    id: number;
    content: string;
};

const Notes = () => {
    const [notes, setNotes] = useState<Note[]>([]);
    const [newNote, setNewNote] = useState('');
    const [editingNoteId, setEditingNoteId] = useState<number | null>(null);
    const [editedContent, setEditedContent] = useState('');
    const userId = localStorage.getItem('userId');
    const navigate = useNavigate();

    useEffect(() => {
        if (!userId) {
            navigate('/login');
            return;
        }
        fetchNotes();
    }, []);

    const fetchNotes = async () => {
        const res = await getNotes(userId);
        setNotes(res);
    };

    const handleAddNote = async () => {
        if (!newNote.trim()) return;
        const addedNote = await addNote(userId, newNote);
        console.log("added note is ", addedNote);
        setNotes((prevNotes) => [...prevNotes, addedNote]);
        setNewNote('');
    };

    const handleStartEdit = (note: Note) => {
        setEditingNoteId(note.id);
        setEditedContent(note.content);
    };

    const handleCancelEdit = () => {
        setEditingNoteId(null);
        setEditedContent('');
    };

    const handleSaveEdit = async () => {
        if (editingNoteId !== null) {
            await updateNote(editingNoteId, editedContent);
            setNotes((prevNotes) =>
                prevNotes.map((note) =>
                    note.id === editingNoteId ? { ...note, content: editedContent } : note
                )
            );
            setEditingNoteId(null);
            setEditedContent('');
        }
    };

    const handleDeleteNote = async (id: number) => {
        await deleteNote(id);
        setNotes((prevNotes) => prevNotes.filter((note) => note.id !== id));
    };


    return (
        <div className='container'>
            <h2>Your Notes</h2>

            <div style={{ display: 'flex', gap: 10 }}>
                <input
                    value={newNote}
                    onChange={(e) => setNewNote(e.target.value)}
                    placeholder="Add a note"
                    style={{ flex: 1, padding: 8 }}
                />
                <button onClick={handleAddNote} style={{ padding: 8 }}>Add</button>
            </div>

            <ul style={{ marginTop: 20, listStyle: 'none', padding: 0 }}>
                {notes.map((note) => (
                    <li
                        key={note.id}
                        style={{
                            padding: 10,
                            border: '1px solid #ccc',
                            borderRadius: 6,
                            marginBottom: 10,
                            display: 'flex',
                            alignItems: 'center',
                            gap: 10,
                            backgroundColor: '#fff'
                        }}
                    >
                        {editingNoteId === note.id ? (
                            <>
                                <input
                                    value={editedContent}
                                    onChange={(e) => setEditedContent(e.target.value)}
                                    style={{ flex: 1, padding: 6 }}
                                />
                                <button onClick={handleSaveEdit}>Save</button>
                                <button onClick={handleCancelEdit}>Cancel</button>
                            </>
                        ) : (
                            <>
                                <span style={{ flex: 1 }}>{note.content}</span>
                                <button onClick={() => handleStartEdit(note)}>Update</button>
                                <button onClick={() => handleDeleteNote(note.id)}>Delete</button>
                            </>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Notes;
