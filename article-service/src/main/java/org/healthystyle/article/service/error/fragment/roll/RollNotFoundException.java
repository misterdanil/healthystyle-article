package org.healthystyle.article.service.error.fragment.roll;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class RollNotFoundException extends AbstractException {
	private Long id;

	public RollNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found roll by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
