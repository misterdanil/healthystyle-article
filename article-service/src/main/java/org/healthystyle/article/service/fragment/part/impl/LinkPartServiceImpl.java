package org.healthystyle.article.service.fragment.part.impl;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.model.fragment.text.part.LinkPart;
import org.healthystyle.article.repository.fragment.text.LinkPartRepository;
import org.healthystyle.article.service.dto.fragment.text.LinkPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.LinkPartUpdateRequest;
import org.healthystyle.article.service.error.fragment.text.part.LinkPartNotFoundException;
import org.healthystyle.article.service.fragment.part.LinkPartService;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@Service
public class LinkPartServiceImpl implements LinkPartService {
	@Autowired
	private LinkPartRepository repository;

	private static final Logger LOG = LoggerFactory.getLogger(LinkPartServiceImpl.class);

	@Override
	public LinkPart findById(Long id) throws ValidationException, LinkPartNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "linkPart");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("text_part.find.id.not_null", "Укажите идентификатор текстовой ссылки для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking link part for existence by id '{}'", id);
		Optional<LinkPart> linkPart = repository.findById(id);
		if (linkPart.isEmpty()) {
			result.reject("link_part.find.not_found", "Не удалось найти текстовую ссылку");
			throw new LinkPartNotFoundException(id, result);
		}
		LOG.info("Got link part successfully by id '{}'", id);

		return linkPart.get();
	}

	@Override
	public LinkPart save(LinkPartSaveRequest saveRequest, Text text) {
		String link = saveRequest.getLink();

		LinkPart linkPart = new LinkPart(saveRequest.getOrder(), text, saveRequest.getText(), link);
		linkPart = repository.save(linkPart);
		LOG.info("Link part was saved successfully: {}", linkPart);

		return linkPart;
	}

	@Override
	public void update(LinkPartUpdateRequest updateRequest, Long id) throws ValidationException, LinkPartNotFoundException {
		LinkPart linkPart = findById(id);

		String source = updateRequest.getSource();
		if (!source.equals(linkPart.getSource())) {
			linkPart.setSource(source);
		}

		linkPart = repository.save(linkPart);
		LOG.info("The link part was update successfully: {}", linkPart);
	}

}
