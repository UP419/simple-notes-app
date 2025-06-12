package com.example.notesapp;

import com.example.notesapp.model.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddNoteToUser() {
        UUID userId = UUID.randomUUID();
        String requestJson = String.format("{\"userId\":\"%s\", \"content\":\"Integration note\"}", userId);

        HttpEntity<String> request = new HttpEntity<>(requestJson, createJsonHeaders());

        ResponseEntity<Note> response = restTemplate.postForEntity("/api/v1/note/add", request, Note.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).isEqualTo("Integration note");
    }

    @Test
    void testGetNotesByUserIdEmpty() {
        UUID userId = UUID.randomUUID();

        ResponseEntity<Note[]> response = restTemplate.getForEntity("/api/v1/note/" + userId, Note[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void testUpdateNoteByIdWithInvalidId() {
        UUID fakeNoteId = UUID.randomUUID();
        String requestJson = String.format("{\"noteId\":\"%s\", \"content\":\"Updated content\"}", fakeNoteId);

        HttpEntity<String> request = new HttpEntity<>(requestJson, createJsonHeaders());

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/note/update", HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void testAddAndThenUpdateNote() {
        UUID userId = UUID.randomUUID();
        String createJson = String.format("{\"userId\":\"%s\", \"content\":\"Original content\"}", userId);

        HttpEntity<String> createRequest = new HttpEntity<>(createJson, createJsonHeaders());

        ResponseEntity<Note> createResponse = restTemplate.postForEntity("/api/v1/note/add", createRequest, Note.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        UUID noteId = createResponse.getBody().getId();
        String updateJson = String.format("{\"noteId\":\"%s\", \"content\":\"Updated content\"}", noteId);

        HttpEntity<String> updateRequest = new HttpEntity<>(updateJson, createJsonHeaders());

        ResponseEntity<String> updateResponse = restTemplate.exchange("/api/v1/note/update", HttpMethod.PUT, updateRequest, String.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).contains("updated");
    }

    @Test
    void testAddAndThenDeleteNote() {
        UUID userId = UUID.randomUUID();
        String createJson = String.format("{\"userId\":\"%s\", \"content\":\"To be deleted\"}", userId);

        HttpEntity<String> createRequest = new HttpEntity<>(createJson, createJsonHeaders());

        ResponseEntity<Note> createResponse = restTemplate.postForEntity("/api/v1/note/add", createRequest, Note.class);
        UUID noteId = createResponse.getBody().getId();

        ResponseEntity<String> deleteResponse = restTemplate.exchange("/api/v1/note/delete/" + noteId, HttpMethod.DELETE, null, String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).contains("deleted");
    }

    @Test
    void testDeleteNoteById_NotFound() {
        UUID noteId = UUID.randomUUID();

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/note/delete/" + noteId, HttpMethod.DELETE, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
