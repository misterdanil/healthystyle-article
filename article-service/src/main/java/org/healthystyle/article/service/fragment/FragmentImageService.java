package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.FragmentImage;
import org.healthystyle.article.service.dto.fragment.FragmentImageSaveRequest;
import org.healthystyle.article.service.dto.fragment.FragmentImageUpdateRequest;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.fragment.image.FragmentImageNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface FragmentImageService {
	FragmentImage findById(Long id) throws ValidationException, FragmentImageNotFoundException;

	FragmentImage save(FragmentImageSaveRequest saveRequest, Fragment fragment) throws ValidationException, ImageNotFoundException;

	void update(FragmentImageUpdateRequest updateRequest, Long fragmentImageId)
			throws ValidationException, FragmentImageNotFoundException, ImageNotFoundException;
}
