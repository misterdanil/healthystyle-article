package org.healthystyle.article.web.dto;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public class ArticleDto {
	private Long id;
	private String title;
	private ImageDto image;
	private Float averageMark;
	private Long author;
	private CategoryDto category;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ArticleSourceDto> sources;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Integer countViews;
	private Instant createdOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ImageDto getImage() {
		return image;
	}

	public void setImage(ImageDto image) {
		this.image = image;
	}

	public Float getAverageMark() {
		return averageMark;
	}

	public void setAverageMark(Float averageMark) {
		this.averageMark = averageMark;
	}

	public Long getAuthor() {
		return author;
	}

	public void setAuthor(Long author) {
		this.author = author;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public List<ArticleSourceDto> getSources() {
		return sources;
	}

	public void setSources(List<ArticleSourceDto> sources) {
		this.sources = sources;
	}

	public Integer getCountViews() {
		return countViews;
	}

	public void setCountViews(Integer countViews) {
		this.countViews = countViews;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

}
