package org.healthystyle.article.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.healthystyle.article.model.Image;
import org.healthystyle.article.model.Source;
import org.healthystyle.article.repository.ImageRepository;
import org.healthystyle.article.service.ImageService;
import org.healthystyle.article.service.SourceService;
import org.healthystyle.article.service.client.ImageServiceClient;
import org.healthystyle.article.service.dto.ImageSaveRequest;
import org.healthystyle.article.service.dto.ImageUpdateRequest;
import org.healthystyle.article.service.dto.SourceSaveRequest;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageRepository repository;
	@Autowired
	private ImageServiceClient imageClient;
	@Autowired
	private SourceService sourceService;
	@Autowired
	private Validator validator;

	private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

	@Override
	public Image findById(Long id) throws ValidationException, ImageNotFoundException {
		BindingResult result = new MapBindingResult(new HashMap<>(), "image");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("image.find.id.not_null", "Укажите идентификатор для поиска изображения");
			throw new ValidationException("The id is null", result);
		}

		Optional<Image> image = repository.findById(id);
		LOG.debug("Checking image for existence by id '{}'", id);
		if (image.isEmpty()) {
			result.reject("image.find.id.not_found", "Не удалось найти изображение по данному идентификатору");
			throw new ImageNotFoundException(id, result);
		}
		LOG.info("Got image successfully by id '{}'", id);

		return image.get();
	}

	@Override
	public Image save(ImageSaveRequest saveRequest) throws ValidationException, ImageNotFoundException {
		LOG.debug("Validating image: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "image");
		validator.validate(saveRequest, result);
		Long imageId = saveRequest.getImageId();
		if (!imageClient.existsById(imageId)) {
			result.reject("image.save.image_id.not_exists", "Изображения с данным идентификатором не существует");
			throw new ImageNotFoundException(imageId, result);
		}

		LOG.debug("Image is OK: {}", saveRequest);
		Image image = new Image(imageId);

		SourceSaveRequest sourceSaveRequest = saveRequest.getSource();
		if (sourceSaveRequest != null) {
			LOG.debug("Saving source: {}", sourceSaveRequest);
			Source source = sourceService.save(sourceSaveRequest);
			image.setSource(source);
		}

		image = repository.save(image);
		LOG.info("The image was saved successfully: {}", image);

		return image;
	}

	@Override
	public void update(ImageUpdateRequest updateRequest, Long id) throws ValidationException, ImageNotFoundException {
		LOG.debug("Validating image: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "image");
		validator.validate(updateRequest, result);
		if (id == null) {
			result.reject("image.update.id.not_null", "Укажите идентификатор изображения для обновления");
		}
		if (result.hasErrors()) {
			throw new ValidationException("The data is invalid. Result: %s", result, result);
		}
		Long imageId = updateRequest.getImageId();
		if (!imageClient.existsById(imageId)) {
			result.reject("image.update.image_id.not_exists", "Изображения с данным идентификатором не существует");
			throw new ImageNotFoundException(imageId, result);
		}

		Image image = findById(id);
		image.setImageId(imageId);

		LOG.debug("Image is OK: {}", updateRequest);

		image = repository.save(image);
		LOG.info("The image was updated successfully: {}", image);
	}
}
