package org.healthystyle.article.model;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(indexes = { @Index(name = "category_title_idx", columnList = "title", unique = true),
		@Index(name = "category_parent_category_id_idx", columnList = "parent_category_id") })
public class Category {
	@Id
	@SequenceGenerator(name = "category_generator", sequenceName = "category_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "category_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, unique = true)
	private String title;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Article> articles;
	@ManyToOne
	@JoinColumn(name = "parent_category_id")
	private Category parentCategory;
	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Category> subCategories;
	@Temporal(TemporalType.TIMESTAMP)
	private Instant createdOn = Instant.now();

	public Category() {
		super();
	}

	public Category(String title) {
		super();
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Article> getArticles() {
		if (articles == null) {
			articles = new ArrayList<>();
		}
		return articles;
	}

	public void addArticles(List<Article> articles) {
		getArticles().addAll(articles);
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Category> getSubCategories() {
		if (subCategories == null) {
			subCategories = new ArrayList<>();
		}
		return subCategories;
	}

	public void addSubCategories(List<Category> subCategories) {
		getSubCategories().addAll(subCategories);
	}

}
