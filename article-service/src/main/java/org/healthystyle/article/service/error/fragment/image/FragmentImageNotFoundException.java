package org.healthystyle.article.service.error.fragment.image;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class FragmentImageNotFoundException extends AbstractException {
	private Long id;

	public FragmentImageNotFoundException(Long id, BindingResult result) {
		super(result, "Could not found fragment image by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
