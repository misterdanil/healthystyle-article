package org.healthystyle.article.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Source {
	@Id
	@SequenceGenerator(name = "source_generator", sequenceName = "source_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "source_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String link;

	public Source() {
		super();
	}

	public Source(String description, String link) {
		super();
		this.description = description;
		this.link = link;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
