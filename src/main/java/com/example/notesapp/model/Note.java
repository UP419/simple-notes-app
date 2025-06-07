package com.example.notesapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notes")
public class Note {
    @Id
    private Long id;

    private Long userId;

    private String content;

    public Note(Long userId, String content){
        this.userId = userId;
        this.content = content;
    }

}
