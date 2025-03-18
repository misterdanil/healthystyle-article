package org.healthystyle.article.service.error.fragment.text;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class TextNotFoundException extends AbstractException {
	private Long id;

	public TextNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found text by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
