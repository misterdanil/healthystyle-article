package org.healthystyle.article.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.mark.MarkArticle;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(indexes = { @Index(name = "article_title_idx", columnList = "title"),
		@Index(name = "article_category_id_idx", columnList = "category_id") })
public class Article {
	@Id
	@SequenceGenerator(name = "article_generator", sequenceName = "article_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(generator = "article_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String title;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "image_id")
	private Image image;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Fragment> fragments;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<MarkArticle> marks;
	@Column(name = "user_id", nullable = false)
	private Long author;
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ArticleSource> sources;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comment> comments;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<View> views;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn;

	public Article() {
		super();
	}

	public Article(String title) {
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Fragment> getFragments() {
		if (fragments == null) {
			fragments = new ArrayList<>();
		}
		return fragments;
	}

	public void addFragment(Fragment fragment) {
		getFragments().add(fragment);
	}

	public void addFragments(List<Fragment> fragments) {
		getFragments().addAll(fragments);
	}

	public List<MarkArticle> getMarks() {
		if (marks == null) {
			marks = new ArrayList<>();
		}
		return marks;
	}

	public void addMarks(List<MarkArticle> marks) {
		getMarks().addAll(marks);
	}

	public Long getAuthor() {
		return author;
	}

	public void setAuthor(Long author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ArticleSource> getSources() {
		if (sources == null) {
			sources = new ArrayList<>();
		}
		return sources;
	}

	public void addSources(List<ArticleSource> sources) {
		this.sources = sources;
	}

	public List<Comment> getComments() {
		if (comments == null) {
			comments = new ArrayList<>();
		}
		return comments;
	}

	public void addComments(List<Comment> comments) {
		getComments().addAll(comments);
	}

	public List<View> getViews() {
		if (views == null) {
			views = new ArrayList<>();
		}
		return views;
	}

	public void addViews(List<View> views) {
		this.views = views;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
}
