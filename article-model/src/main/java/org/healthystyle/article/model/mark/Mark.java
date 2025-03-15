package org.healthystyle.article.model.mark;

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
@Table(name = "mark", indexes = @Index(name = "mark_value_idx", columnList = "value", unique = true))
public class Mark {
	@Id
	@SequenceGenerator(name = "mark_generator", sequenceName = "mark_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "mark_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, unique = true)
	private Integer value;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn;

	public Mark() {
		super();
	}

	public Mark(Integer value) {
		super();
		this.value = value;
		createdOn = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public Integer getValue() {
		return value;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

}
