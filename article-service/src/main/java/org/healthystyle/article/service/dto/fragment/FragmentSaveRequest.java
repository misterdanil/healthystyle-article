package org.healthystyle.article.service.dto.fragment;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FragmentSaveRequest {
	private String title;
	@NotNull(message = "Укажите порядок для данного фрагмента")
	@Positive(message = "Порядок должен быть положительным и больше нуля")
	private Integer order;
	@NotEmpty(message = "Фрагмент не может быть пустым")
	@Valid
	private List<OrderSaveRequest> orders;

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

	public List<OrderSaveRequest> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderSaveRequest> orders) {
		this.orders = orders;
	}

}
