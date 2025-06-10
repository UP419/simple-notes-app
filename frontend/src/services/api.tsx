const API_BASE = 'http://localhost:8080/api/v1';

export async function login(userName, password) {
    const response = await fetch(`${API_BASE}/user/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userName, password }),
    });
    if (!response.ok) throw new Error('Login failed');
    return response.json();
}

export async function signup(userName, password) {
    const response = await fetch(`${API_BASE}/user/signup`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userName, password }),
    });
    if (!response.ok) throw new Error('Signup failed');
    return response.json();
}

export async function getNotes(userId) {
    const response = await fetch(`${API_BASE}/note/${userId}`);
    if (!response.ok) throw new Error('Failed to fetch notes');
    return response.json();
}

export async function addNote(userId, content) {
    const response = await fetch(`${API_BASE}/note/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, content }),
    });
    if (!response.ok) throw new Error('Failed to add note');
    return response.text();
}

export async function updateNote(noteId, content) {
    const response = await fetch(`${API_BASE}/note/update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ noteId, content }),
    });
    if (!response.ok) throw new Error('Failed to update note');
    return response.text();
}

export async function deleteNote(noteId) {
    const response = await fetch(`${API_BASE}/note/delete/${noteId}`, {
        method: 'DELETE',
    });
    if (!response.ok) throw new Error('Failed to delete note');
    return response.text();
}
