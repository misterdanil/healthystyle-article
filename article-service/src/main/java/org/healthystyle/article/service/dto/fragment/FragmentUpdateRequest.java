package org.healthystyle.article.service.dto.fragment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FragmentUpdateRequest {
	private String title;
	@NotNull(message = "Укажите порядок для данного фрагмента")
	@Positive(message = "Порядок должен быть положительным и больше нуля")
	private Integer order;

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

}
