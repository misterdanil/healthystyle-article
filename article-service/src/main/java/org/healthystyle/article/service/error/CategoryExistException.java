package org.healthystyle.article.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class CategoryExistException extends AbstractException {
	private String title;

	public CategoryExistException(String title, BindingResult result) {
		super(result, "A category has already existed by title '%s'", title);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
