package org.healthystyle.article.service.fragment.impl;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.healthystyle.article.model.fragment.Roll;
import org.healthystyle.article.model.fragment.RollElement;
import org.healthystyle.article.repository.fragment.RollElementRepository;
import org.healthystyle.article.service.dto.fragment.RollElementSaveRequest;
import org.healthystyle.article.service.dto.fragment.RollElementUpdateRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.OrderField;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollElementNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollIdField;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.fragment.RollElementService;
import org.healthystyle.article.service.fragment.RollService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class RollElementServiceImpl implements RollElementService {
	@Autowired
	private RollElementRepository repository;
	@Autowired
	private RollService rollService;
	@Autowired
	private Validator validator;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(RollElementServiceImpl.class);

	@Override
	public RollElement findById(Long id) throws ValidationException, RollElementNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "roll");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("roll_element.find.id.not_null", "Укажите идентификатор элемента списка для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking roll element for existence by id '{}'", id);
		Optional<RollElement> rollElement = repository.findById(id);
		if (rollElement.isEmpty()) {
			result.reject("roll_element.find.not_found", "Не удалось найти элемент списка");
			throw new RollElementNotFoundException(result, new IdField(id));
		}
		LOG.info("Got roll element successfully by id '{}'", id);

		return rollElement.get();
	}

	@Override
	public RollElement findByRollAndOrder(Long rollId, Integer order)
			throws ValidationException, RollElementNotFoundException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_ROLL_AND_ORDER_PARAM_NAMES, rollId, order);

		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "rollElement");
		LOG.debug("Validating params: {}", params);
		if (rollId == null) {
			result.reject("roll_element.find.roll_id.not_null", "Укажите идентификатор списка для поиска его элемента");
		}
		if (order == null) {
			result.reject("roll_element.find.order.not_null", "Укажите порядок для поиска его элемента списка");
		}
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		LOG.debug("Checking roll element existence by params: {}", params);
		RollElement rollElement = repository.findByRollAndOrder(rollId, order);
		if(rollElement == null) {
			result.reject("roll_element.find.not_found", "Не удалось найти элемент списка");
			throw new RollElementNotFoundException(result, new RollIdField(rollId), new OrderField(order));
		}
		LOG.info("Got roll element successfully by params: {}", params);

		return rollElement;
	}

	@Override
	public Page<RollElement> findByRoll(Long rollId, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_ROLL_PARAM_NAMES, rollId, page, limit);

		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "rollElement");
		LOG.debug("Validating params: {}", params);
		if (rollId == null) {
			result.reject("roll_element.find.roll_id.not_null",
					"Укажите идентификатор списка для поиска его элементов");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<RollElement> rollElements = repository.findByRoll(rollId, PageRequest.of(page, limit));
		LOG.info("Got roll elements successfully by params: {}", params);

		return rollElements;
	}

	@Override
	public RollElement save(RollElementSaveRequest saveRequest, Long rollId)
			throws ValidationException, RollNotFoundException, OrderExistException, PreviousOrderNotFoundException {
		LOG.debug("Validating roll element: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "rollElement");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("Roll element is invalid: %s. Result: %s", result, saveRequest, result);
		}

		LOG.debug("Getting roll by id '{}' to save roll element", rollId);
		Roll roll = rollService.findById(rollId);

		Integer order = saveRequest.getOrder();
		LOG.debug("Checking order for existence '{}'", order);
		if (repository.existsByRollAndOrder(rollId, order)) {
			result.rejectValue("order", "roll_element.save.order.exists", "Данный порядок у списка уже существует");
			throw new OrderExistException(order, result);
		}
		if (order > 1 && !repository.existsByRollAndOrder(rollId, order - 1)) {
			result.rejectValue("order", "roll_element.save.order.previous_not_found", "Прошлый порядок не был найден");
			throw new PreviousOrderNotFoundException(order, result);
		}

		LOG.debug("The roll element is OK: {}", saveRequest);

		RollElement rollElement = new RollElement(roll, saveRequest.getText(), order);
		rollElement = repository.save(rollElement);
		LOG.info("The roll element was saved successfully: {}", rollElement);

		return rollElement;
	}

	@Override
	public void update(RollElementUpdateRequest updateRequest, Long rollElementId)
			throws ValidationException, PreviousOrderNotFoundException, RollElementNotFoundException {
		LOG.debug("Validating roll element: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "rollElement");
		validator.validate(updateRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("Roll element is invalid: %s. Result: %s", result, updateRequest, result);
		}

		LOG.debug("Checking roll element '{}' for existence to update roll element", rollElementId);
		RollElement rollElement = findById(rollElementId);
		Long rollId = rollElement.getRoll().getId();
		Integer currentOrder = rollElement.getOrder();

		Integer order = updateRequest.getOrder();
		if (order != currentOrder) {
			LOG.debug("Checking order for existence '{}'", order);
			try {
				RollElement currentRollElement = findByRollAndOrder(rollId, order);
				LOG.debug("Found current order: {}. Changing order to '{}'", currentRollElement, currentOrder);
				currentRollElement.setOrder(currentOrder);
				repository.save(currentRollElement);
			} catch (RollElementNotFoundException e) {
				if (order > 1 && !repository.existsByRollAndOrder(rollId, order - 1)) {
					result.rejectValue("order", "roll_element.save.order.previous_not_found",
							"Прошлый порядок не был найден");
					throw new PreviousOrderNotFoundException(order, result);
				}
			}
			rollElement.setOrder(order);
		}

		String text = updateRequest.getText();
		if (!text.equals(rollElement.getText())) {
			LOG.debug("Setting text: {}", text);
			rollElement.setText(text);
		}

		LOG.debug("The roll element is OK: {}", updateRequest);

		rollElement = repository.save(rollElement);
		LOG.info("The roll element was updated successfully: {}", rollElement);
	}

	@Override
	@Transactional
	public void deleteById(Long id) throws ValidationException, RollElementNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "order");
		if (id == null) {
			result.reject("roll_element.delete.id.not_null", "Укажите идентификатор элемента списка для удаления");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Getting roll element by id '{}' to delete it", id);
		RollElement rollElement = findById(id);
		Integer order = rollElement.getOrder();
		Long rollId = rollElement.getRoll().getId();

		LOG.debug("Shifting all the next roll elements back whose order is more than {} and roll id is {}", order,
				rollId);
		repository.delete(rollElement);
		repository.shiftAllNextBack(order, id);
		LOG.info("The roll element was delete successfully by id '{}' and all the next elements were shifted back");
	}

}
