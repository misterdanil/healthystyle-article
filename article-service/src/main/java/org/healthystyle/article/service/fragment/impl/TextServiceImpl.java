package org.healthystyle.article.service.fragment.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.model.fragment.text.part.TextPart;
import org.healthystyle.article.repository.fragment.TextRepository;
import org.healthystyle.article.service.dto.fragment.TextSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.TextPartSaveRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.service.fragment.TextService;
import org.healthystyle.article.service.fragment.part.TextPartService;
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
public class TextServiceImpl implements TextService {
	@Autowired
	private TextRepository repository;
	@Autowired
	private Validator validator;
	@Autowired
	private TextPartService textPartService;

	private static final Logger LOG = LoggerFactory.getLogger(TextServiceImpl.class);

	@Override
	public Text findById(Long id) throws ValidationException, TextNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "roll");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("text.find.id.not_null", "Укажите идентификатор текста для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking text for existence by id '{}'", id);
		Optional<Text> text = repository.findById(id);
		if (text.isEmpty()) {
			result.reject("text.find.not_found", "Не удалось найти текста фрагмента");
			throw new TextNotFoundException(id, result);
		}
		LOG.info("Got text successfully by id '{}'", id);

		return text.get();
	}

	@Override
	public Text save(TextSaveRequest saveRequest, Fragment fragment)
			throws ValidationException, OrderExistException, PreviousOrderNotFoundException, TextNotFoundException {
		if (fragment == null) {
			throw new NullPointerException("Fragment is null");
		}

		LOG.debug("Validating text: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "text");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The text is invalid: %s. Result: %s", result, saveRequest, result);
		}

		Text text = new Text(fragment, saveRequest.getOrder());
		text = repository.save(text);

		List<TextPartSaveRequest> textPartSaveRequests = saveRequest.getTextParts();
		LOG.debug("Saving text parts: {}", textPartSaveRequests);
		for (TextPartSaveRequest textPartSaveRequest : textPartSaveRequests) {
			LOG.debug("Saving text part: {}", textPartSaveRequest);
			TextPart textPart = textPartService.save(textPartSaveRequest, text.getId());
			text.addTextPart(textPart);
		}

		LOG.info("The text was saved successfully: {}", text);

		return text;

	}

}
