package org.healthystyle.article.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "article_source")
public class ArticleSource {
	@Id
	@SequenceGenerator(name = "article_source_generator", sequenceName = "article_source_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "article_source_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private Integer order;
	@ManyToOne
	@JoinColumn(name = "article_id")
	private Article article;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "source_id", nullable = false)
	private Source source;

	public ArticleSource() {
		super();
	}

	public ArticleSource(Integer order, Article article, Source source) {
		super();
		this.order = order;
		this.article = article;
		this.source = source;
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

	public Article getArticle() {
		return article;
	}

	public Source getSource() {
		return source;
	}
}
