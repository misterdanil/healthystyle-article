package org.healthystyle.article.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class OrderExistException extends AbstractException {
	private Integer order;

	public OrderExistException(Integer order, BindingResult result) {
		super(result, "A order has already existed: %s", result, order);
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}

}
