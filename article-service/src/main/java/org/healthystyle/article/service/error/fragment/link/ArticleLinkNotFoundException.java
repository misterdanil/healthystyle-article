package org.healthystyle.article.service.error.fragment.link;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class ArticleLinkNotFoundException extends AbstractException {
	private Long id;

	public ArticleLinkNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found article link by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
