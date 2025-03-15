package org.healthystyle.article.model.fragment;

import java.time.Instant;

import org.healthystyle.article.model.Image;

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
@Table(name = "fragment_image", indexes = @Index(name = "fragment_image_order_id_idx", columnList = "order_id"))
public class FragmentImage {
	@Id
	@SequenceGenerator(name = "fragment_image_generator", sequenceName = "fragment_image_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "fragment_image_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "image_id", nullable = false)
	private Image image;
	@OneToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	@ManyToOne
	@JoinColumn(name = "fragment_id", nullable = false)
	private Fragment fragment;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Instant createdOn;

	public FragmentImage() {
		super();
	}

	public FragmentImage(Image image, Order order, Fragment fragment) {
		super();
		this.image = image;
		this.order = order;
		this.fragment = fragment;
		createdOn = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
