package org.healthystyle.article.service.fragment.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Quote;
import org.healthystyle.article.model.fragment.Roll;
import org.healthystyle.article.repository.fragment.RollRepository;
import org.healthystyle.article.service.dto.fragment.RollElementSaveRequest;
import org.healthystyle.article.service.dto.fragment.RollSaveRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.quote.QuoteNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.fragment.RollElementService;
import org.healthystyle.article.service.fragment.RollService;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@Service
class RollServiceImpl implements RollService {
	@Autowired
	private RollRepository repository;
	@Autowired
	private RollElementService rollElementService;

	private static final Logger LOG = LoggerFactory.getLogger(RollServiceImpl.class);

	@Override
	public Roll findById(Long id) throws ValidationException, RollNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "roll");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("roll.find.id.not_null", "Укажите идентификатор списка для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking roll for existence by id '{}'", id);
		Optional<Roll> roll = repository.findById(id);
		if (roll.isEmpty()) {
			result.reject("roll.find.not_found", "Не удалось найти список фрагмента");
			throw new RollNotFoundException(id, result);
		}
		LOG.info("Got roll successfully by id '{}'", id);

		return roll.get();
	}

	@Override
	public Roll save(RollSaveRequest saveRequest, Fragment fragment)
			throws ValidationException, RollNotFoundException, OrderExistException, PreviousOrderNotFoundException {
		if (fragment == null) {
			throw new NullPointerException("The fragment is null");
		}

		Roll roll = new Roll(fragment, saveRequest.getOrder());
		LOG.debug("Saving roll");
		roll = repository.save(roll);

		List<RollElementSaveRequest> rollElementSaveRequests = saveRequest.getRollElements();
		LOG.debug("Saving roll elements: {}", rollElementSaveRequests);
		for (RollElementSaveRequest rollElementSaveRequest : rollElementSaveRequests) {
			LOG.debug("Saving roll element: {}", rollElementSaveRequest);
			roll.addRollElement(rollElementService.save(rollElementSaveRequest, roll.getId()));
		}

		LOG.info("The roll was saved successfully: {}", roll);
		return roll;
	}

}
