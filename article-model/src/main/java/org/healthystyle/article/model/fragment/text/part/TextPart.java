package org.healthystyle.article.model.fragment.text.part;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "text_part")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class TextPart {
	@Id
	@SequenceGenerator(name = "text_part_generator", sequenceName = "text_part_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "text_part_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private Integer order;

	public TextPart() {
		super();
	}

	public TextPart(Integer order) {
		super();
		this.order = order;
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

}
