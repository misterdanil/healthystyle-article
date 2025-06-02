package org.healthystyle.article.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.ArticleSource;
import org.healthystyle.article.model.Source;
import org.healthystyle.article.repository.ArticleSourceRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.ArticleSourceService;
import org.healthystyle.article.service.SourceService;
import org.healthystyle.article.service.dto.ArticleSourceSaveRequest;
import org.healthystyle.article.service.dto.ArticleSourceUpdateRequest;
import org.healthystyle.article.service.dto.SourceSaveRequest;
import org.healthystyle.article.service.error.ArticleIdField;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.ArticleSourceNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.OrderField;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
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
public class ArticleSourceServiceImpl implements ArticleSourceService {
	@Autowired
	private ArticleSourceRepository repository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private Validator validator;
	@Autowired
	private SourceService sourceService;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(ArticleSourceServiceImpl.class);

	@Override
	public ArticleSource findById(Long id) throws ValidationException, ArticleSourceNotFoundException {
		BindingResult result = new MapBindingResult(new HashMap<>(), "image");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("article_source.find.id.not_null", "Укажите идентификатор для поиска источника статьи");
			throw new ValidationException("The id is null", result);
		}

		Optional<ArticleSource> articleSource = repository.findById(id);
		LOG.debug("Checking article source for existence by id '{}'", id);
		if (articleSource.isEmpty()) {
			result.reject("article_source.find.id.not_found",
					"Не удалось найти источника статьи по данному идентификатору");
			throw new ArticleSourceNotFoundException(result, new IdField(id));
		}
		LOG.info("Got article source successfully by id '{}'", id);

		return articleSource.get();
	}

	@Override
	public ArticleSource findByArticleAndOrder(Long articleId, Integer order)
			throws ValidationException, ArticleSourceNotFoundException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_ARTICLE_AND_ORDER_PARAM_NAMES, articleId, order);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new HashMap<>(), "image");

		LOG.debug("Checking article id for not null: {}", articleId);
		if (articleId == null) {
			result.reject("article_source.find.article_id.not_null",
					"Укажите идентификатор статьи для поиска источников");
		}

		LOG.debug("Checking order for not null: {}", order);
		if (order == null) {
			result.reject("article_source.find.order.not_null", "Укажите порядок для поиска источника статьи");
		}

		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		ArticleSource articleSource = repository.findByArticleAndOrder(articleId, order);
		LOG.debug("Checking article source for existence: {}", articleSource);
		if (articleSource == null) {
			result.reject("article_source.find.not_found", "Не удалось найти источник статьи");
			throw new ArticleSourceNotFoundException(result, new ArticleIdField(articleId), new OrderField(order));
		}
		LOG.info("Got article source successfully by article id '{}' and order '{}'", articleId, order);

		return articleSource;
	}

	@Override
	public Page<ArticleSource> findByArticle(Long articleId, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_ARTICLE_PARAM_NAMES, articleId, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new HashMap<>(), "image");

		LOG.debug("Checking article id for not null: {}", articleId);
		if (articleId == null) {
			result.reject("article_source.find.article_id.not_null",
					"Укажите идентификатор статьи для поиска источников");
		}

		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);

		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		Page<ArticleSource> articleSources = repository.findByArticle(articleId, PageRequest.of(page, limit));
		LOG.info("Got article sources successfully by article id '{}'", articleId);

		return articleSources;
	}

	@Override
	public ArticleSource save(ArticleSourceSaveRequest saveRequest, Long articleId)
			throws ValidationException, ArticleNotFoundException, OrderExistException, PreviousOrderNotFoundException {
		LOG.debug("Validating article source: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "articleSource");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The data is invalid. Result: %s", result);
		}

		Article article = articleService.findById(articleId);
		Integer order = saveRequest.getOrder();
		LOG.debug("Checking existence for order: {}", order);
		if (repository.existsByArticleAndOrder(articleId, order)) {
			result.reject("article_source.save.order.exists", "Порядок уже существует");
			throw new OrderExistException(order, result);
		}
		if (order > 1 && !repository.existsByArticleAndOrder(articleId, order - 1)) {
			LOG.debug("Checking existence for previous order: {}", order);
			result.reject("article_source.save.order.not_found_previous", "Не удалось найти прошлый порядок");
			throw new PreviousOrderNotFoundException(order, result);
		}

		SourceSaveRequest sourceSaveRequest = saveRequest.getSource();
		LOG.debug("The article source is OK. Saving source: {}", sourceSaveRequest);
		Source source = sourceService.save(sourceSaveRequest);

		ArticleSource articleSource = new ArticleSource(order, article, source);
		articleSource = repository.save(articleSource);
		LOG.info("The article source was saved successfully: {}", articleSource);

		return articleSource;
	}

	@Override
	public void update(ArticleSourceUpdateRequest updateRequest, Long articleSourceId)
			throws ValidationException, PreviousOrderNotFoundException, ArticleSourceNotFoundException {
		LOG.debug("Validating article source: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "articleSource");
		validator.validate(updateRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The data is invalid. Result: %s", result);
		}

		ArticleSource articleSource = findById(articleSourceId);
		Long articleId = articleSource.getArticle().getId();
		Integer order = updateRequest.getOrder();
		if (!order.equals(articleSource.getOrder())) {
			LOG.debug("Checking existence for order: {}", order);
			try {
				ArticleSource existedArticleSource = findByArticleAndOrder(articleId, order);
				existedArticleSource.setOrder(articleSource.getOrder());
				repository.save(existedArticleSource);
			} catch (ArticleSourceNotFoundException e) {
				LOG.debug("Checking existence for previous order: {}", order);
				if (order > 1 && !repository.existsByArticleAndOrder(articleId, order - 1)) {
					result.reject("article_source.update.order.not_found_previous", "Не удалось найти прошлый порядок");
					throw new PreviousOrderNotFoundException(order, result);
				}
			}
			articleSource.setOrder(order);
			repository.save(articleSource);
			LOG.info("The article source was updated successfully: {}", articleSource);

		}
	}

}
