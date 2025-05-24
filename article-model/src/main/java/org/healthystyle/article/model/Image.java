package org.healthystyle.article.model;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Image {
	@Id
	@SequenceGenerator(name = "image_generator", sequenceName = "image_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "image_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "source_id")
	private Source source;
	private Long imageId;
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		return Objects.equals(id, other.id);
	}

}
