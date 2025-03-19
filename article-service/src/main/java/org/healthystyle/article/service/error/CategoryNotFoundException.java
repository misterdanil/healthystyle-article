package org.healthystyle.article.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class CategoryNotFoundException extends AbstractException {
	private Long id;

	public CategoryNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found category by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
