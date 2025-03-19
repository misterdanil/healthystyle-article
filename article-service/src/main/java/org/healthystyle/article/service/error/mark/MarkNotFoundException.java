package org.healthystyle.article.service.error.mark;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class MarkNotFoundException extends AbstractException {
	private Integer value;

	public MarkNotFoundException(Integer value, BindingResult result) {
		super(result, "Could not found mark by value '%s", value);
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
