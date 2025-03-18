package org.healthystyle.article.service.fragment.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.fragment.ArticleLink;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.repository.fragment.ArticleLinkRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.dto.fragment.ArticleLinkSaveRequest;
import org.healthystyle.article.service.dto.fragment.ArticleLinkUpdateRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentIdField;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkNotFoundException;
import org.healthystyle.article.service.fragment.ArticleLinkService;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@Service
class ArticleLinkServiceImpl implements ArticleLinkService {
	@Autowired
	private ArticleLinkRepository repository;
	@Autowired
	private ArticleService articleService;

	private static final Logger LOG = LoggerFactory.getLogger(ArticleLinkServiceImpl.class);

	@Override
	public ArticleLink findById(Long id) throws ValidationException, ArticleLinkNotFoundException {
		BindingResult result = new MapBindingResult(new HashMap<>(), "articleLink");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("article_link.find.id.not_null", "Укажите идентификатор ссылки на статью");
			throw new ValidationException("The id is null", result);
		}

		Optional<ArticleLink> articleLink = repository.findById(id);
		if (articleLink.isEmpty()) {
			result.reject("article_link.find.id.not_found",
					"Не удалось найти ссылки на статью по данному идентификатору");
			throw new ArticleLinkNotFoundException(id, result);
		}
		LOG.info("Got article link by id '{}' successfully", id);

		return articleLink.get();
	}

	@Override
	public ArticleLink save(ArticleLinkSaveRequest saveRequest, Fragment fragment)
			throws ValidationException, ArticleNotFoundException, ArticleLinkExistException {
//		LOG.debug("Validating article link: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(new LinkedHashMap<>(), "articleLink");
//		validator.validate(saveRequest, result);
//		if (result.hasErrors()) {
//			throw new ValidationException("Article link is invalid: %s. Result: %s", result, saveRequest, result);
//		}

		Long articleId = saveRequest.getArticleId();
		Long fragmentId = fragment.getId();
		LOG.debug("Checking article existence by fragment id '{}' and link id '{}' to save article link '{}'",
				fragmentId, articleId, saveRequest);
		if (repository.existsByFragmentAndLink(fragmentId, articleId)) {
			result.reject("article_link.save.article_id.exists",
					"В рамках данного фрагмента уже существует ссылка на эту статью");
			throw new ArticleLinkExistException(result, new FragmentIdField(articleId));
		}

		Article article = articleService.findById(articleId);

		LOG.info("The article link is OK: {}", saveRequest);

		Integer order = saveRequest.getOrder();

		ArticleLink articleLink = new ArticleLink(article, fragment, order);
		articleLink = repository.save(articleLink);
		LOG.info("The article link was saved successfully: {}", articleLink);

		return articleLink;
	}

	@Override
	public void update(ArticleLinkUpdateRequest updateRequest, Long articleLinkId) throws ValidationException,
			ArticleLinkNotFoundException, ArticleLinkExistException, ArticleNotFoundException {
//		LOG.debug("Validating article link: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(new LinkedHashMap<>(), "articleLink");
//		validator.validate(updateRequest, result);
//		if (result.hasErrors()) {
//			throw new ValidationException("Article link is invalid: %s. Result: %s", result, updateRequest, result);
//		}

		ArticleLink articleLink = findById(articleLinkId);

		Long articleId = updateRequest.getArticleId();
		if (articleId != articleLink.getLink().getId()) {
			Long fragmentId = articleLink.getFragment().getId();
			LOG.debug("Checking article existence by fragment id '{}' and link id '{}' to update article link '{}'",
					fragmentId, articleId, articleLinkId);
			if (repository.existsByFragmentAndLink(fragmentId, articleId)) {
				result.reject("article_link.update.article_id.exists",
						"В рамках данного фрагмента уже существует ссылка на эту статью");
				throw new ArticleLinkExistException(result, new FragmentIdField(articleId));
			}

			Article article = articleService.findById(articleId);
			LOG.info("The article link is OK: {}", updateRequest);

			articleLink.setLink(article);
			articleLink = repository.save(articleLink);
			LOG.info("The article link was saved successfully: {}", articleLink);
		} else {
			LOG.debug("No changed detected of article link '{}': {}", articleLinkId, updateRequest);
		}
	}

}
