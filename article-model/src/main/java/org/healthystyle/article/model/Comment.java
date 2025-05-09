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

@Entity
@Table(indexes = @Index(name = "comment_article_id_idx", columnList = "article_id"))
public class Comment {
	@Id
	@SequenceGenerator(name = "comment_generator", sequenceName = "comment_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "comment_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String text;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@ManyToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;
	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment replyTo;
	@OneToMany(mappedBy = "replyTo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comment> replies;
	@Column(name = "created_on", nullable = false)
	private Instant createdOn = Instant.now();

	public Comment() {
		super();
	}

	public Comment(String text, Long userId, Article article) {
		super();
		this.text = text;
		this.userId = userId;
		this.article = article;
	}

	public Comment(String text, Long userId, Article article, Comment replyTo) {
		super();
		this.text = text;
		this.userId = userId;
		this.article = article;
		this.replyTo = replyTo;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getUserId() {
		return userId;
	}

	public Article getArticle() {
		return article;
	}

	public Comment getReplyTo() {
		return replyTo;
	}

	public List<Comment> getReplies() {
		if (replies == null) {
			replies = new ArrayList<>();
		}
		return replies;
	}

	public void addReplies(List<Comment> replies) {
		getReplies().addAll(replies);
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

}
