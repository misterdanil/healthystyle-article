package org.healthystyle.article.model.fragment;

import java.time.Instant;


import org.healthystyle.article.model.Article;

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

@Entity
@Table(name = "article_link", indexes = @Index(name = "article_link_order_id_idx", columnList = "order_id"))
public class ArticleLink {
	@Id
	@SequenceGenerator(name = "article_link_generator", sequenceName = "article_link_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "article_link_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article link;
	@ManyToOne
	@JoinColumn(name = "fragment_id", nullable = false)
	private Fragment fragment;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Order order;
	@Column(nullable = false)
	private Instant createdOn;

	public ArticleLink() {
		super();
	}

	public ArticleLink(Article link, Fragment fragment, Order order) {
		super();
		this.link = link;
		this.fragment = fragment;
		this.order = order;
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

	public Fragment getFragment() {
		return fragment;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
