package org.healthystyle.article.model.fragment.text.part;

import java.time.Instant;

import org.healthystyle.article.model.fragment.text.Text;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "text_part")
@Inheritance(strategy = InheritanceType.JOINED)
public class TextPart {
	@Id
	@SequenceGenerator(name = "text_part_generator", sequenceName = "text_part_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "text_part_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private Integer order;
	@ManyToOne
	@JoinColumn(name = "text_id", nullable = false)
	private Text text;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public TextPart() {
		super();
	}

	public TextPart(Integer order, Text text) {
		super();
		this.order = order;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Text getText() {
		return text;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
	
}
