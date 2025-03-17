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
@Table(indexes = @Index(name = "order_fragment_id_idx", columnList = "fragment_id"))
public class Order {
	@Id
	@SequenceGenerator(name = "order_generator", sequenceName = "order_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "order_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fragment_id", nullable = false)
	private Fragment fragment;
	@Column(nullable = false)
	private Integer order;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn;

	public Order() {
		super();
	}

	public Order(Fragment fragment, Integer order) {
		super();
		this.fragment = fragment;
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public Fragment getFragment() {
		return fragment;
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
