package com.componentware.wasserdatabase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Entity
@Table(name="User")
public class User {
    @Column(nullable = false, unique = true)
    private String name;
    private String email;
    private String description;
    @Column(nullable = false)
    private String password;
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

    public User(String name, String email, String description, String password) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.password = password;
    }
    public User() {
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
