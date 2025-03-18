package org.healthystyle.article.model.fragment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(name = "roll_order_id_idx", columnList = "order_id"))
public class Roll extends Order {
	@Id
	@SequenceGenerator(name = "roll_generator", sequenceName = "roll_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "roll_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToMany(mappedBy = "roll", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<RollElement> rollElements;
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public Roll() {
		super();
	}

	public Roll(Fragment fragment, Integer order) {
		super(fragment, order);
	}

	public Long getId() {
		return id;
	}

	public List<RollElement> getRollElements() {
		if (rollElements == null) {
			rollElements = new ArrayList<>();
		}
		return rollElements;
	}

	public void addRollElements(List<RollElement> rollElements) {
		getRollElements().addAll(rollElements);
	}

	public void addRollElement(RollElement rollElement) {
		getRollElements().add(rollElement);
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

}
