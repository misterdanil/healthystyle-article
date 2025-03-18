package org.healthystyle.article.model.fragment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.healthystyle.article.model.Article;

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
@Table(indexes = @Index(name = "fragment_article_id_order_idx", columnList = "article_id,order"))
public class Fragment {
	@Id
	@SequenceGenerator(name = "fragment_generator", sequenceName = "fragment_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "fragment_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 300)
	private String title;
	@Column(nullable = false)
	private Integer order;
	@ManyToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;
	@OneToMany(mappedBy = "fragment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Order> orders;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Instant createdOn;

	public Fragment() {
		super();
	}

	public Fragment(Integer order, Article article, String title) {
		super();
		this.order = order;
		this.article = article;
		this.title = title;
		createdOn = Instant.now();
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<Order> getOrders() {
		if (orders == null) {
			orders = new ArrayList<>();
		}
		return orders;
	}

	public void addOrders(List<Order> orders) {
		getOrders().addAll(orders);
	}

	public void addOrder(Order order) {
		getOrders().add(order);
	}

}
