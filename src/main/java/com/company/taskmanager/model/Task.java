package com.company.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;


    @OneToOne
    @JoinColumn(name = "user_id")
    private Users assignedTo;
    private boolean completed;
}
