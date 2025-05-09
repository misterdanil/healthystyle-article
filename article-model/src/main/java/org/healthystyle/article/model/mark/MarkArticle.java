package org.healthystyle.article.model.mark;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "mark_article", indexes = { @Index(name = "mark_article_article_id_idx", columnList = "article_id"),
		@Index(name = "mark_article_user_id_idx", columnList = "user_id") })
public class MarkArticle {
	@Id
	@SequenceGenerator(name = "mark_article_generator", sequenceName = "mark_article_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "mark_article_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Mark mark;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false)
	private Instant createdOn = Instant.now();

	public MarkArticle() {
		super();
	}

	public MarkArticle(Article article, Mark mark, Long userId) {
		super();
		this.article = article;
		this.mark = mark;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public Article getArticle() {
		return article;
	}

	public Mark getMark() {
		return mark;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public Long getUserId() {
		return userId;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

}
