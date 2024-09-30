package com.perscholas.task_management_system_11.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated (EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
