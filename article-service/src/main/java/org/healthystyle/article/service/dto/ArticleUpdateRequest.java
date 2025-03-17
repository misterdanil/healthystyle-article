package org.healthystyle.article.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArticleUpdateRequest {
	@NotBlank(message = "Укажите название статьи")
	private String title;
	@NotNull(message = "Укажите категорию")
	private Long categoryId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
