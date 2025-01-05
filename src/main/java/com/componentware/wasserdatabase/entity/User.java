package com.componentware.wasserdatabase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User")
public class User {
    private String name;
    private String email;
    private String description;
    private String password;
    @ManyToOne
    @JoinColumn(name = "sensorSender_id", nullable = false)
    private SensorSender sensorSender;
    @ManyToOne
    @JoinColumn(name = "Container_id", nullable = false)
    private Container Container;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String email, String description, String password,SensorSender sensorSender, Container Container) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.password = password;
        this.sensorSender = sensorSender;
        this.Container = Container;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
