package org.healthystyle.article.service.fragment.impl;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Quote;
import org.healthystyle.article.repository.fragment.QuoteRepository;
import org.healthystyle.article.service.dto.fragment.QuoteSaveRequest;
import org.healthystyle.article.service.dto.fragment.QuoteUpdateRequest;
import org.healthystyle.article.service.error.fragment.quote.QuoteNotFoundException;
import org.healthystyle.article.service.fragment.QuoteService;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@Service
public class QuoteServiceImpl implements QuoteService {
	@Autowired
	private QuoteRepository repository;

	private static final Logger LOG = LoggerFactory.getLogger(QuoteServiceImpl.class);

	@Override
	public Quote findById(Long id) throws ValidationException, QuoteNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "quote");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("quote.find.id.not_null", "Укажите идентификатор цитаты для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking quote for existence by id '{}'", id);
		Optional<Quote> quote = repository.findById(id);
		if (quote.isEmpty()) {
			result.reject("quote.find.not_found", "Не удалось найти цитату");
			throw new QuoteNotFoundException(id, result);
		}
		LOG.info("Got quote successfully by id '{}'", id);

		return quote.get();
	}

	@Override
	public Quote save(QuoteSaveRequest saveRequest, Fragment fragment) {
		if (fragment == null) {
			throw new NullPointerException("The fragment is null");
		}

		Quote quote = new Quote(fragment, saveRequest.getOrder(), saveRequest.getName(), saveRequest.getDescription());
		quote = repository.save(quote);
		LOG.info("Quote was saved successfully: {}", quote);

		return quote;
	}

	@Override
	public void update(QuoteUpdateRequest updateRequest, Long quoteId)
			throws ValidationException, QuoteNotFoundException {
		LOG.debug("Getting quote by id '{}' to update it: {}", quoteId, updateRequest);
		Quote quote = findById(quoteId);

		String name = updateRequest.getName();
		String oldName = quote.getName();
		if (!oldName.equals(name)) {
			LOG.debug("The name was changed from '{}' to '{}'", oldName, name);
			quote.setName(name);
		} else {
			LOG.debug("No changed with name: {}", name);
		}

		String description = updateRequest.getDescription();
		String oldDescription = quote.getText();
		if (!oldDescription.equals(description)) {
			LOG.debug("The text was changed from '{}' to '{}'", oldDescription, description);
			quote.setText(description);
		} else {
			LOG.debug("No changed with text: {}", description);
		}

		quote = repository.save(quote);
		LOG.info("The quote was update successfully: {}", quote);
	}

}
