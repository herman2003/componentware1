package com.componentware.wasserdatabase.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Nachricht")
public class Nachricht {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private LocalDateTime timestamp;
	@Column
	private String Status;
	@Column
	private String info;
	@Column
	private float wasserstand;
	@ManyToOne
	@JoinColumn(name = "sensorSender_id", nullable = false)
	private SensorSender sensorSender;
	public Nachricht(LocalDateTime timestamp, String Status, String info, SensorSender sensorSender, float wasserstand) {
		this.timestamp = timestamp;
		this.Status = Status;
		this.info = info;
		this.sensorSender = sensorSender;
		this.wasserstand = wasserstand;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}



	public SensorSender getSensorSender() {
		return sensorSender;
	}

	public void setSensorSender(SensorSender sensorSender) {
		this.sensorSender = sensorSender;
	}
	public float getWasserstand() {
		return wasserstand;
	}

	public void setWasserstand(float minimalStand) {
		this.wasserstand = minimalStand;
	}
}
