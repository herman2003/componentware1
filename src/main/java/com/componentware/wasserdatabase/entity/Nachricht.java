package com.componentware.wasserdatabase.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@NoArgsConstructor
@Entity
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
	@JoinColumn(name = "sender_id", nullable = false)
	private Sender sender;
	public Nachricht(LocalDateTime timestamp, String Status, String info, Sender sender, float wasserstand) {
		this.timestamp = timestamp;
		this.Status = Status;
		this.info = info;
		this.sender = sender;
		this.wasserstand = wasserstand;
	}
	public Nachricht(){

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
	public float getWasserstand() {
		return wasserstand;
	}

	public void setWasserstand(float minimalStand) {
		this.wasserstand = minimalStand;
	}
}
