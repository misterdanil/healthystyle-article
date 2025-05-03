package org.healthystyle.article.model.fragment;

import java.time.Instant;

import org.healthystyle.article.model.Image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
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
@DiscriminatorValue("image")
public class FragmentImage extends Order {
	@Id
	@SequenceGenerator(name = "fragment_image_generator", sequenceName = "fragment_image_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "fragment_image_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "image_id", nullable = false)
	private Image image;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Instant createdOn;

	public FragmentImage() {
		super();
	}

	public FragmentImage(Image image, Fragment fragment, Integer order) {
		super(fragment, order);
		this.image = image;
		createdOn = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
