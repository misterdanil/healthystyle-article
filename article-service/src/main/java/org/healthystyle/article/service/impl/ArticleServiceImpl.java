package org.healthystyle.article.service.impl;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.ArticleSource;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.repository.ArticleRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.client.ImageServiceClient;
import org.healthystyle.article.service.dto.ArticleSaveRequest;
import org.healthystyle.article.service.dto.ArticleSourceSaveRequest;
import org.healthystyle.article.service.dto.ArticleUpdateRequest;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;
import org.healthystyle.article.service.fragment.ArticleSourceService;
import org.healthystyle.article.service.fragment.FragmentService;
import org.healthystyle.article.service.util.LogTemplate;
import org.healthystyle.article.service.util.ParamsChecker;
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
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleRepository repository;
	@Autowired
	private Validator validator;
	@Autowired
	private FragmentService fragmentService;
	@Autowired
	private ImageServiceClient imageServiceClient;
	@Autowired
	private ArticleSourceService articleSourceService;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Override
	public Page<Article> find(int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_PARAM_NAMES, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Article> articles = repository.find(PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	@Override
	public Page<Article> findByTitle(String title, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_TITLE_PARAM_NAMES, title, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		if (title == null || title.isBlank()) {
			result.reject("article.find.title.not_blank", "Укажите название статьи для поиска");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Article> articles = repository.findByTitle(title, PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	@Override
	public Page<Article> findByCategory(Long categoryId, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_CATEGORY_PARAM_NAMES, categoryId, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		if (categoryId == null) {
			result.reject("article.find.category_id.not_null", "Укажите идентификатор категории для поиска статей");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Article> articles = repository.findByCategory(categoryId, PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	@Override
	public Page<Article> findByAuthor(Long author, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_AUTHOR_PARAM_NAMES, author, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		if (author == null) {
			result.reject("article.find.author.not_null", "Укажите автора для поиска статей");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Article> articles = repository.findByAuthor(author, PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	@Override
	public Page<Article> findMostWatched(Long categoryId, Long authorId, Instant start, Instant end, int page,
			int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_MOST_WATCHED_PARAM_NAMES, categoryId, authorId, start, end,
				page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		ParamsChecker.checkDates(start, end, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Article> articles = repository.findMostWatched(categoryId, authorId, start, end,
				PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	@Override
	public Page<Article> findMostMarked(Long categoryId, Long authorId, Instant start, Instant end, int page, int limit)
			throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_MOST_MARKED_PARAM_NAMES, categoryId, authorId, start, end,
				page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		ParamsChecker.checkDates(start, end, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Article> articles = repository.findMostMarked(categoryId, authorId, start, end,
				PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	@Override
	public Article save(ArticleSaveRequest saveRequest, Long categoryId) throws ValidationException {
		LOG.debug("Validating article: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "article");
		validator.validate(saveRequest, result);
		if (categoryId == null) {
			result.reject("article.save.category_id.not_null", "Укажите категорию для сохранения статьи");
		}
		if (result.hasErrors()) {
			throw new ValidationException("The data is invalid. Article: %s. Category id: %s. Result: %s", result,
					saveRequest, categoryId, result);
		}
		
		Article article = new Article(saveRequest.getTitle());
		article = repository.save(article);
		Long articleId = article.getId();
		
		List<FragmentSaveRequest> fragmentSaveRequests = saveRequest.getFragments();
		LOG.debug("Saving fragments: {}", fragmentSaveRequests);
		for (FragmentSaveRequest fragmentSaveRequest : fragmentSaveRequests) {
			Fragment fragment = fragmentService.save(fragmentSaveRequest, articleId);
			article.addFragment(fragment);
		}
		
		List<ArticleSourceSaveRequest> articleSourceSaveRequests = saveRequest.getSources();
		LOG.debug("Saving sources: {}", articleSourceSaveRequests);
		for(ArticleSourceSaveRequest articleSourceSaveRequest : articleSourceSaveRequests) {
			ArticleSource articleSource = articleSourceService.save(articleSourceSaveRequest, articleId);
		}
		
		
	}

	@Override
	public void update(ArticleUpdateRequest updateRequest, Long articleId) {
		// TODO Auto-generated method stub

	}

}
