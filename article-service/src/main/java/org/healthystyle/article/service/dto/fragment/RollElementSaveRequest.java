package org.healthystyle.article.service.dto.fragment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RollElementSaveRequest {
	@NotBlank(message = "Введите значение элемента списка")
	private String text;
	@NotNull(message = "Укажите порядок элемента списка")
	@Positive(message = "Порядок элемента списка должен быть положительным или больше нуля")
	private Integer order;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
