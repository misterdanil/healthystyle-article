package org.healthystyle.article.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ArticleSourceSaveRequest {
	@NotNull(message = "Укажите порядок следования источника")
	@Positive(message = "Порядок следования статей должен быть положительным числом и больше нуля")
	private Integer order;
	@Valid
	@NotNull(message = "Укажите источник")
	private SourceSaveRequest source;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public SourceSaveRequest getSource() {
		return source;
	}

	public void setSource(SourceSaveRequest source) {
		this.source = source;
	}

}
