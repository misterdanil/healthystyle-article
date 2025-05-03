package org.healthystyle.article.model.fragment;

import java.time.Instant;

import org.healthystyle.article.model.Article;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "article_link", indexes = @Index(name = "article_link_order_id_idx", columnList = "order_id"))
@DiscriminatorValue("link")
public class ArticleLink extends Order{
	@Id
	@SequenceGenerator(name = "article_link_generator", sequenceName = "article_link_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "article_link_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article link;
	@Column(nullable = false)
	private Instant createdOn = Instant.now();

	public ArticleLink() {
		super();
	}

	public ArticleLink(Article link, Fragment fragment, Integer order) {
		super(fragment, order);
		this.link = link;
	}

	public Long getId() {
		return id;
	}

	public Article getLink() {
		return link;
	}

	public void setLink(Article link) {
		this.link = link;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
