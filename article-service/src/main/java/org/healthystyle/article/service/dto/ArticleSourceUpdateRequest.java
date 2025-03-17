package org.healthystyle.article.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ArticleSourceUpdateRequest {
	@NotNull(message = "Укажите порядок следования источника")
	@Positive(message = "Порядок следования источников статей должен быть положительным числом и больше нуля")
	private Integer order;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
