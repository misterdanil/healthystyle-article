package org.healthystyle.article.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class PreviousOrderNotFoundException extends AbstractException {
	private Integer order;

	public PreviousOrderNotFoundException(Integer order, BindingResult result) {
		super(result, "Could not found previous order of '%s'", order);
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}
}
