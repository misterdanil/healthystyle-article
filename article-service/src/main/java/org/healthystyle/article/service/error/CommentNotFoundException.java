package org.healthystyle.article.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class CommentNotFoundException extends AbstractException {
	private Long id;

	public CommentNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found comment by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
