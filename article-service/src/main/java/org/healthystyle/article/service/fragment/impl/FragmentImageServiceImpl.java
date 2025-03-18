package org.healthystyle.article.service.fragment.impl;

import java.util.HashMap;
import java.util.Optional;

import org.healthystyle.article.model.Image;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.FragmentImage;
import org.healthystyle.article.repository.fragment.FragmentImageRepository;
import org.healthystyle.article.service.ImageService;
import org.healthystyle.article.service.dto.ImageSaveRequest;
import org.healthystyle.article.service.dto.ImageUpdateRequest;
import org.healthystyle.article.service.dto.fragment.FragmentImageSaveRequest;
import org.healthystyle.article.service.dto.fragment.FragmentImageUpdateRequest;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.fragment.image.FragmentImageNotFoundException;
import org.healthystyle.article.service.fragment.FragmentImageService;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@Service
class FragmentImageServiceImpl implements FragmentImageService {
	@Autowired
	private FragmentImageRepository repository;
	@Autowired
	private ImageService imageService;

	private static final Logger LOG = LoggerFactory.getLogger(FragmentImageServiceImpl.class);

	@Override
	public FragmentImage findById(Long id) throws ValidationException, FragmentImageNotFoundException {
		BindingResult result = new MapBindingResult(new HashMap<>(), "fragmentImage");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("fragment_image.find.id.not_null", "Укажите идентификатор изображения фрагмента");
			throw new ValidationException("The id is null", result);
		}

		Optional<FragmentImage> fragmentImage = repository.findById(id);
		if (fragmentImage.isEmpty()) {
			result.reject("fragment_image.find.id.not_found",
					"Не удалось найти изображения фрагмента по данному идентификатору");
			throw new FragmentImageNotFoundException(id, result);
		}
		LOG.info("Got fragment image by id '{}' successfully", id);

		return fragmentImage.get();
	}

	@Override
	public FragmentImage save(FragmentImageSaveRequest saveRequest, Fragment fragment)
			throws ValidationException, ImageNotFoundException {
		if (fragment == null) {
			throw new NullPointerException("Fragment is null");
		}

		LOG.debug("Saving fragment image: {}", saveRequest);

		ImageSaveRequest imageSaveRequest = saveRequest.getImage();
		LOG.debug("Saving image to save fragment image: {}", imageSaveRequest);
		Image image = imageService.save(imageSaveRequest);

		FragmentImage fragmentImage = new FragmentImage(image, fragment, saveRequest.getOrder());
		fragmentImage = repository.save(fragmentImage);
		LOG.info("The fragment image was saved successfully: {}", fragmentImage);

		return fragmentImage;
	}

	@Override
	public void update(FragmentImageUpdateRequest updateRequest, Long fragmentImageId)
			throws ValidationException, FragmentImageNotFoundException, ImageNotFoundException {
		LOG.debug("Getting fragment image by id '{}'", fragmentImageId);
		FragmentImage fragmentImage = findById(fragmentImageId);

		ImageUpdateRequest imageUpdateRequest = updateRequest.getImage();
		Long imageId = fragmentImage.getImage().getId();
		LOG.debug("Updating image '{}' for fragment image '{}'", imageId, fragmentImageId);
		imageService.update(imageUpdateRequest, imageId);
	}

}
