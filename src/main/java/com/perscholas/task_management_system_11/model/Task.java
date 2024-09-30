package com.perscholas.task_management_system_11.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private  Employee employee;
    private String status;

}
