package org.healthystyle.article.model.fragment;

import org.healthystyle.article.model.fragment.text.Text;

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
@Table(indexes = @Index(name = "order_fragment_id_idx", columnList = "fragment_id"))
public class Order {
	@Id
	@SequenceGenerator(name = "order_generator", sequenceName = "order_seq", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "order_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fragment_id", nullable = false)
	private Fragment fragment;
	@Column(nullable = false)
	private Integer order;
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Text text;
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private ArticleLink articleLink;
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private FragmentImage image;
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Quote quote;
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Roll roll;

	public Order() {
		super();
	}

	public Order(Fragment fragment, Integer order) {
		super();
		this.fragment = fragment;
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public ArticleLink getArticleLink() {
		return articleLink;
	}

	public void setArticleLink(ArticleLink articleLink) {
		this.articleLink = articleLink;
	}

	public FragmentImage getImage() {
		return image;
	}

	public void setImage(FragmentImage image) {
		this.image = image;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public Roll getRoll() {
		return roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
	}

}
