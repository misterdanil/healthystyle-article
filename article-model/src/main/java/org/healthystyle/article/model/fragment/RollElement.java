package org.healthystyle.article.model.fragment;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "roll_element", indexes = @Index(name = "roll_element_roll_id_idx", columnList = "roll_id"))
public class RollElement {
	@Id
	@SequenceGenerator(name = "roll_element_generator", sequenceName = "roll_element_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "roll_element_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "roll_id", nullable = false)
	private Roll roll;
	@Column(nullable = false, length = 500)
	private String text;
	@Column(name = "_order", nullable = false)
	private Integer order;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public RollElement() {
		super();
	}

	public RollElement(Roll roll, String text, Integer order) {
		super();
		this.roll = roll;
		this.text = text;
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public Roll getRoll() {
		return roll;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

}
