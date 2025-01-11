package com.componentware.wasserdatabase.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@Entity
@Table(name="Auftrag")
public class Auftrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String info;

    @Column
    private String status;

    @Column
    private String type;

    @Column
    private LocalDateTime date;

    @Column
    private float preis;

    // Relation Many-to-One avec User (Un user peut avoir plusieurs Aufträge)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relation Many-to-One avec Sender (Un Sender peut avoir plusieurs Aufträge)
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Sender sender;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public float getPreis() {
        return preis;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
