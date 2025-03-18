package org.healthystyle.article.service.fragment.part.impl;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.model.fragment.text.part.BoldPart;
import org.healthystyle.article.model.fragment.text.part.CursivePart;
import org.healthystyle.article.model.fragment.text.part.LinkPart;
import org.healthystyle.article.model.fragment.text.part.TextPart;
import org.healthystyle.article.repository.fragment.text.TextPartRepository;
import org.healthystyle.article.service.dto.fragment.text.BoldPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.BoldPartUpdateRequest;
import org.healthystyle.article.service.dto.fragment.text.CursivePartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.CursivePartUpdateRequest;
import org.healthystyle.article.service.dto.fragment.text.LinkPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.LinkPartUpdateRequest;
import org.healthystyle.article.service.dto.fragment.text.TextPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.TextPartUpdateRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.OrderField;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextIdField;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.service.error.fragment.text.part.LinkPartNotFoundException;
import org.healthystyle.article.service.error.fragment.text.part.TextPartNotFoundException;
import org.healthystyle.article.service.fragment.TextService;
import org.healthystyle.article.service.fragment.part.LinkPartService;
import org.healthystyle.article.service.fragment.part.TextPartService;
import org.healthystyle.article.service.util.LogTemplate;
import org.healthystyle.article.service.util.ParamsChecker;
import org.healthystyle.util.error.IdField;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class TextPartServiceImpl implements TextPartService {
	@Autowired
	private TextPartRepository repository;
	@Autowired
	private Validator validator;
	@Autowired
	private TextService textService;
	@Autowired
	private LinkPartService linkPartService;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(TextPartServiceImpl.class);

	@Override
	public TextPart findById(Long id) throws ValidationException, TextPartNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "textPart");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("text_part.find.id.not_null", "Укажите идентификатор части текста для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking text part for existence by id '{}'", id);
		Optional<TextPart> textPart = repository.findById(id);
		if (textPart.isEmpty()) {
			result.reject("text_part.find.not_found", "Не удалось найти часть текста");
			throw new TextPartNotFoundException(result, new IdField(id));
		}
		LOG.info("Got text part successfully by id '{}'", id);

		return textPart.get();
	}

	@Override
	public TextPart findByTextAndOrder(Long textId, Integer order)
			throws ValidationException, TextPartNotFoundException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_TEXT_AND_ORDER_PARAM_NAMES, textId, order);

		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "textPart");
		LOG.debug("Validating params: {}", params);
		if (textId == null) {
			result.reject("text_part.find.text_id.not_null", "Укажите идентификатор текста для поиска его части");
		}
		if (order == null) {
			result.reject("text_part.find.order.not_null", "Укажите порядок для поиска части текста");
		}
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		LOG.debug("Checking text part for existence by params: {}", params);
		TextPart textPart = repository.findByTextAndOrder(textId, order);
		if (textPart == null) {
			result.reject("text_part.find.not_found", "Не удалось найти часть текста");
			throw new TextPartNotFoundException(result, new TextIdField(textId), new OrderField(order));
		}
		LOG.info("Got text part successfully by params: {}", params);

		return textPart;
	}

	@Override
	public Page<TextPart> findByText(Long textId, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_TEXT_PARAM_NAMES, textId, page, limit);

		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "textPart");
		LOG.debug("Validating params: {}", params);
		if (textId == null) {
			result.reject("text_part.find.text_id.not_null", "Укажите идентификатор текста для поиска частей");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("Text part is invalid: %s. Result: %s", result, params, result);
		}

		Page<TextPart> textParts = repository.findByText(textId, PageRequest.of(page, limit));
		LOG.info("Got text parts successfully by params: {}", params);

		return textParts;
	}

	@Override
	public TextPart save(TextPartSaveRequest saveRequest, Long textId)
			throws ValidationException, OrderExistException, PreviousOrderNotFoundException, TextNotFoundException {
		LOG.debug("Validating text part: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "textPart");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("Text part is invalid: %s. Result: %s", result, saveRequest, result);
		}

		LOG.debug("Getting text by id '{}' to save text part: {}", textId, saveRequest);
		Text text = textService.findById(textId);

		Integer order = saveRequest.getOrder();
		LOG.debug("Checking order for existence '{}'", order);
		if (repository.existsByTextAndOrder(textId, order)) {
			result.rejectValue("order", "text_part.save.order.exists", "Данный порядок у текста уже существует");
			throw new OrderExistException(order, result);
		}
		if (order > 1 && !repository.existsByTextAndOrder(textId, order - 1)) {
			result.rejectValue("order", "text.save.order.previous_not_found", "Прошлый порядок не был найден");
			throw new PreviousOrderNotFoundException(order, result);
		}

		LOG.debug("The text part is OK: {}", saveRequest);

		TextPart textPart;
		if (saveRequest instanceof LinkPartSaveRequest) {
			textPart = linkPartService.save((LinkPartSaveRequest) saveRequest, text);
		} else if (saveRequest instanceof CursivePartSaveRequest) {
			textPart = new CursivePart(order, text, saveRequest.getText());
			textPart = repository.save(textPart);
		} else if (saveRequest instanceof BoldPartSaveRequest) {
			textPart = new BoldPart(order, text, saveRequest.getText());
			textPart = repository.save(textPart);
		} else {
			throw new RuntimeException("Unknown type: " + saveRequest.getClass().getName());
		}

		LOG.info("The text part was saved successfully: {}", textPart);

		return textPart;
	}

	@Override
	public void update(TextPartUpdateRequest updateRequest, Long textPartId) throws ValidationException,
			TextPartNotFoundException, PreviousOrderNotFoundException, LinkPartNotFoundException {
		LOG.debug("Validating text part: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "textPart");
		validator.validate(updateRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The text part is invalid: %s. Result: %s", result, updateRequest, result);
		}

		LOG.debug("Getting text part by id '{}' to update it", textPartId);
		TextPart textPart = findById(textPartId);
		Integer currentOrder = textPart.getOrder();
		Long textId = textPart.getText().getId();

		Integer newOrder = updateRequest.getOrder();
		if (newOrder != currentOrder) {
			LOG.debug("Checking order for existence '{}'", newOrder);
			try {
				TextPart existedTextPart = findByTextAndOrder(textId, newOrder);
				existedTextPart.setOrder(currentOrder);
				repository.save(existedTextPart);
			} catch (TextPartNotFoundException e) {
				LOG.debug("Checking existence for previous order: {}", newOrder);
				if (newOrder > 1 && !repository.existsByTextAndOrder(textId, newOrder - 1)) {
					result.reject("text_part.update.order.previous_not_found", "Не удалось найти прошлый порядок");
					throw new PreviousOrderNotFoundException(newOrder, result);
				}
			}
			textPart.setOrder(newOrder);
		}

		String value = updateRequest.getText();
		if (!value.equals(textPart.getValue())) {
			textPart.setValue(value);
		}

		if (!updateRequest.support(textPart)) {
			LOG.debug("Detected another update request: {}. It doesn't support {}", updateRequest.getClass(),
					TextPart.class);
			if (updateRequest.getClass() == BoldPartUpdateRequest.class) {
				BoldPart boldPart = new BoldPart(textPart.getOrder(), textPart.getText(), textPart.getValue());
				LOG.debug("Deleting current text part and saving new bold part with same data: {}", boldPart);
				repository.delete(textPart);
				repository.save(boldPart);
			} else if (updateRequest.getClass() == CursivePartUpdateRequest.class) {
				CursivePart cursivePart = new CursivePart(textPart.getOrder(), textPart.getText(), textPart.getValue());
				LOG.debug("Deleting current text part and saving new cursive part with same data: {}", cursivePart);
				repository.delete(textPart);
				repository.save(cursivePart);
			} else if (updateRequest.getClass() == LinkPartUpdateRequest.class) {
				LOG.debug("Deleting current text part and saving new link part with same data: {}", updateRequest);
				repository.delete(textPart);
				linkPartService.update((LinkPartUpdateRequest) updateRequest, textId);
			}
		} else if (updateRequest instanceof LinkPartUpdateRequest) {
			linkPartService.update((LinkPartUpdateRequest) updateRequest, textId);
		}
	}

}
