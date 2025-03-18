package org.healthystyle.article.service.error.fragment.text.part;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class LinkPartNotFoundException extends AbstractException {
	private Long id;

	public LinkPartNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found link part by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
