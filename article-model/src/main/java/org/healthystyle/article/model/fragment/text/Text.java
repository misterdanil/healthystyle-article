package org.healthystyle.article.model.fragment.text;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.model.fragment.text.part.TextPart;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(name = "text_order_id_idx", columnList = "order_id"))
public class Text {
	@Id
	@SequenceGenerator(name = "text_generator", sequenceName = "text_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "text_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	@OneToMany(mappedBy = "text", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<TextPart> textParts;
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public Text() {
		super();
	}

	public Text(Order order) {
		super();
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<TextPart> getTextParts() {
		if (textParts == null) {
			textParts = new ArrayList<>();
		}
		return textParts;
	}

	public void addTextParts(List<TextPart> textParts) {
		this.textParts = textParts;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
