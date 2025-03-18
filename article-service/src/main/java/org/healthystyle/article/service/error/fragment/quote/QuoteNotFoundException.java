package org.healthystyle.article.service.error.fragment.quote;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class QuoteNotFoundException extends AbstractException {
	private Long id;

	public QuoteNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found quote by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
