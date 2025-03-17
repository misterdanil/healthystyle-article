package org.healthystyle.article.service;

import org.healthystyle.article.model.Image;
import org.healthystyle.article.service.dto.ImageSaveRequest;
import org.healthystyle.article.service.dto.ImageUpdateRequest;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface ImageService {
	Image findById(Long id) throws ValidationException, ImageNotFoundException;

	Image save(ImageSaveRequest saveRequest) throws ValidationException, ImageNotFoundException;

	void update(ImageUpdateRequest updateRequest, Long imageId) throws ValidationException, ImageNotFoundException;
}
