package org.healthystyle.article.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CategorySaveRequest {
	@NotBlank(message = "Укажите название категории")
	private String title;
	@NotNull(message = "Укажите порядок категории")
	@Positive(message = "Порядок должен быть положительным числом и больше нуля")
	private Integer order;
	private Long parentCategoryId;

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

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

}
