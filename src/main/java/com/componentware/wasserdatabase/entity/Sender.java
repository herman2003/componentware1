package com.componentware.wasserdatabase.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Entity
@Table(name="Sender")
public class Sender {//Container mit Sensor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String location;
    @Column
    private float minimalStand;
    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;
    @OneToMany(mappedBy = "sender")
    private List<Auftrag> auftrags;
    public Sender(Long id, String location, float minimalStand, Sensor sensor, User user, Container container) {
        this.id = id;
        this.location = location;
        this.minimalStand = minimalStand;
        this.sensor = sensor;
        this.user = user;
        this.container = container;
    }
    public Sender( String location, float minimalStand, Sensor sensor, User user, Container container) {
        this.location = location;
        this.minimalStand = minimalStand;
        this.sensor = sensor;
        this.user = user;
        this.container = container;
    }
    public Sender() {
    }
    public Long getId() {
        return id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getMinimalStand() {
        return minimalStand;
    }

    public void setMinimalStand(float wasserStand) {
        this.minimalStand = wasserStand;
    }

}
