package org.healthystyle.article.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class SourceNotFoundException extends AbstractException {
	private Long id;

	public SourceNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found source by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
