package org.healthystyle.article.service.dto.fragment;

import jakarta.validation.constraints.NotNull;

public abstract class OrderSaveRequest {
	@NotNull(message = "Укажите порядок части фрагмента")
	private Integer order;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
