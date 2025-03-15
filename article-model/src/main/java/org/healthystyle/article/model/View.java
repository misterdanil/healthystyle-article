package org.healthystyle.article.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(indexes = @Index(name = "view_created_on_idx", columnList = "created_on"))
public class View {
	@Id
	@SequenceGenerator(name = "view_generator", sequenceName = "view_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "view_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name = "user_id")
	private Long userId;
	private String ip;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false)
	private Instant createdOn = Instant.now();

	public View() {
		super();
	}

	public View(Long userId) {
		super();
		this.userId = userId;
	}

	public View(String ip) {
		super();
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public String getIp() {
		return ip;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
