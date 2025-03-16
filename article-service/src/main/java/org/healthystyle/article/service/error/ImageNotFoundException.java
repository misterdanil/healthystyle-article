package org.healthystyle.article.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class ImageNotFoundException extends AbstractException {
	private Long imageId;

	public ImageNotFoundException(Long imageId, BindingResult result) {
		super(result, "Could not found image by id '%s'", imageId);
		this.imageId = imageId;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

}
