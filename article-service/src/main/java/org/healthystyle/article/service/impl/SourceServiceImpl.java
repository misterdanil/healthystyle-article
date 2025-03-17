package org.healthystyle.article.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.healthystyle.article.model.Image;
import org.healthystyle.article.model.Source;
import org.healthystyle.article.repository.SourceRepository;
import org.healthystyle.article.service.SourceService;
import org.healthystyle.article.service.dto.SourceSaveRequest;
import org.healthystyle.article.service.dto.SourceUpdateRequest;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.SourceNotFoundException;
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
public class SourceServiceImpl implements SourceService {
	@Autowired
	private SourceRepository repository;
	@Autowired
	private Validator validator;

	private static final Logger LOG = LoggerFactory.getLogger(SourceServiceImpl.class);

	@Override
	public Source findById(Long id) throws ValidationException, SourceNotFoundException {
		BindingResult result = new MapBindingResult(new HashMap<>(), "source");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("source.find.id.not_null", "Укажите идентификатор для поиска источника");
			throw new ValidationException("The id is null", result);
		}

		Optional<Source> source = repository.findById(id);
		LOG.debug("Checking source for existence by id '{}'", id);
		if (source.isEmpty()) {
			result.reject("source.find.id.not_found", "Не удалось найти источник по данному идентификатору");
			throw new SourceNotFoundException(id, result);
		}
		LOG.info("Got image successfully by id '{}'", id);

		return source.get();
	}

	@Override
	public Source save(SourceSaveRequest saveRequest) throws ValidationException {
		LOG.debug("Validating source: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "source");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The source is invalid: %s. Result: %s", result, saveRequest, result);
		}

		LOG.debug("The source is OK: {}", saveRequest);

		Source source = new Source(saveRequest.getDescription(), saveRequest.getLink());
		source = repository.save(source);
		LOG.info("The source was saved successfully: {}", saveRequest);

		return source;
	}

	@Override
	public void update(SourceUpdateRequest updateRequest, Long id) throws ValidationException, SourceNotFoundException {
		LOG.debug("Validating source: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "source");
		validator.validate(updateRequest, result);
		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("source.update.id.not_null", "Укажите идентификатор для обновления источника");
		}
		if (result.hasErrors()) {
			throw new ValidationException("The source is invalid: %s. Result: %s", result, updateRequest, result);
		}

		LOG.debug("Getting source by id '{}'", id);
		Source source = findById(id);

		LOG.debug("The source is OK: {}", updateRequest);

		source.setDescription(updateRequest.getDescription());
		source.setLink(updateRequest.getLink());

		source = repository.save(source);
		LOG.info("The source was saved successfully: {}", updateRequest);
	}

}
