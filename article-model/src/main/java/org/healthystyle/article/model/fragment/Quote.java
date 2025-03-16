package org.healthystyle.article.model.fragment;

import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(indexes = @Index(name = "quote_order_id_idx", columnList = "order_id"))
public class Quote {
	@Id
	@SequenceGenerator(name = "quote_generator", sequenceName = "quote_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "quote_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fragment_id", nullable = false)
	private Fragment fragment;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String text;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public Quote() {
		super();
	}

	public Quote(Fragment fragment, Order order, String name, String text) {
		super();
		this.fragment = fragment;
		this.order = order;
		this.name = name;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
