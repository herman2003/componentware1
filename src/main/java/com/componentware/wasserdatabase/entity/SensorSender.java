package com.componentware.wasserdatabase.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name="SensorSender")
public class SensorSender {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String location;
	@Column
	private float minimalStand;
	@ManyToOne
	@JoinColumn(name = "sensor_id", nullable = true)
	private Sensor sensor;
	//private LocalDateTime timestamp;
	//private String info;
	//private String type;
	
//	// One-to-Many relationship with Nachricht
//	@OneToMany(mappedBy = "sensorsender", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Nachricht> nachrichten = new ArrayList<>();
public SensorSender( String location, float minimalStand,Sensor sensor) {
	this.location = location;
	this.minimalStand = minimalStand;
	this.sensor = sensor;
}

	public Long getId() {
		return id;
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
