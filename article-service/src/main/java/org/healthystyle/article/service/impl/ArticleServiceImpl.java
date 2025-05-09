package org.healthystyle.article.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.ArticleSource;
import org.healthystyle.article.model.Category;
import org.healthystyle.article.model.Image;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.repository.ArticleRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.ArticleSourceService;
import org.healthystyle.article.service.CategoryService;
import org.healthystyle.article.service.ImageService;
import org.healthystyle.article.service.UserAccessor;
import org.healthystyle.article.service.ViewService;
import org.healthystyle.article.service.client.FileClient;
import org.healthystyle.article.service.dto.ArticleSaveRequest;
import org.healthystyle.article.service.dto.ArticleSourceSaveRequest;
import org.healthystyle.article.service.dto.ArticleUpdateRequest;
import org.healthystyle.article.service.dto.ImageSaveRequest;
import org.healthystyle.article.service.dto.PeriodSort;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CategoryNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.service.fragment.FragmentService;
import org.healthystyle.article.service.util.LogTemplate;
import org.healthystyle.article.service.util.ParamsChecker;
import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.ValidationException;
import org.healthystyle.util.user.User;
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
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleRepository repository;
	@Autowired
	private Validator validator;
	@Autowired
	private FragmentService fragmentService;
	@Autowired
	private ArticleSourceService articleSourceService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private FileClient imageServiceClient;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserAccessor userAccessor;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Override
	public Article findById(Long id) throws ValidationException, ArticleNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("article.find.id.not_null", "Укажите идентификатор статьи для поиска");
			throw new ValidationException("The id is null", result);
		}
		LOG.debug("Checking article for existence by id '{}'", id);
		Optional<Article> article = repository.findById(id);
		if (article.isEmpty()) {
			result.reject("article.find.not_found", "Не удалось найти статью");
			throw new ArticleNotFoundException(id, result);
		}
			
		LOG.info("Got article successfully by id '{}'", id);

		return article.get();
	}

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
		if (title == null) {
			result.reject("article.find.title.not_null", "Укажите заголовок статьи для поиска");
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
	public Page<Article> findMostWatched(String title, Long categoryId, Long authorId, PeriodSort periodSort, int page,
			int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_MOST_WATCHED_PARAM_NAMES, title, categoryId, authorId,
				periodSort, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		if (title == null) {
			result.reject("article.find.title.not_null", "Укажите заголовок статьи для поиска");
		}
		if (periodSort == null) {
			result.reject("article.find.period_sort.not_null", "Укажите временную сортировку");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Instant start = null;
		Instant end = null;
		if (!periodSort.equals(PeriodSort.ALL_TIME)) {
			end = Instant.now();
			start = getStart(end, periodSort);
		}

		Page<Article> articles = repository.findMostWatched(title, categoryId, authorId, start, end,
				PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	private Instant getStart(Instant end, PeriodSort sort) {
		if (sort.equals(PeriodSort.DAY)) {
			return end.minus(1, ChronoUnit.DAYS);
		} else if (sort.equals(PeriodSort.WEEK)) {
			return ZonedDateTime.ofInstant(end, ZoneId.systemDefault()).minusWeeks(1).toInstant();
		} else if (sort.equals(PeriodSort.MONTH)) {
			return ZonedDateTime.ofInstant(end, ZoneId.systemDefault()).minusMonths(1).toInstant();
		} else if (sort.equals(PeriodSort.YEAR)) {
			return ZonedDateTime.ofInstant(end, ZoneId.systemDefault()).minusYears(1).toInstant();
		} else {
			return null;
		}
	}

	@Override
	public Page<Article> findMostMarked(String title, Long categoryId, Long authorId, PeriodSort periodSort, int page,
			int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_MOST_MARKED_PARAM_NAMES, title, categoryId, authorId,
				periodSort, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "article");
		if (title == null) {
			result.reject("article.find.title.not_null", "Укажите заголовок статьи для поиска");
		}
		if (periodSort == null) {
			result.reject("article.find.period_sort.not_null", "Укажите временную сортировку");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Instant start = null;
		Instant end = null;
		if (!periodSort.equals(PeriodSort.ALL_TIME)) {
			end = Instant.now();
			start = getStart(end, periodSort);
		}

		Page<Article> articles = repository.findMostMarked(title, categoryId, authorId, start, end,
				PageRequest.of(page, limit));
		LOG.info("Got articles successfully by params: {}", params);

		return articles;
	}

	@Override
	@Transactional(rollbackFor = { AbstractException.class, RuntimeException.class })
	public Article save(ArticleSaveRequest saveRequest, Long categoryId)
			throws ValidationException, ImageNotFoundException, ArticleNotFoundException, OrderExistException,
			PreviousOrderNotFoundException, FragmentExistException, ArticleLinkExistException, RollNotFoundException,
			FragmentNotFoundException, CategoryNotFoundException, TextNotFoundException {
		LOG.debug("Validating article: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "article");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The data is invalid. Article: %s. Category id: %s. Result: %s", result,
					saveRequest, categoryId, result);
		}

		Category category = categoryService.findById(categoryId);

		LOG.debug("Getting user to save article: {}", saveRequest);
		User user = userAccessor.getUser();

		Article article = new Article(saveRequest.getTitle(), user.getId(), category);

		ImageSaveRequest imageSaveRequest = saveRequest.getImage();
		if (imageSaveRequest != null) {
			String fileName = imageSaveRequest.getFile().getOriginalFilename();
			imageSaveRequest.setRoot("article");
			String extension = null;
			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = "." + fileName.substring(i + 1);
			}
			imageSaveRequest.setRelativePath(
					"articles/" + UUID.randomUUID().toString() + "/" + UUID.randomUUID().toString() + extension);
			LOG.debug("Saving image: {}", imageSaveRequest);
			Image image = imageService.save(imageSaveRequest);
			article.setImage(image);
		}

		article = repository.save(article);
		Long articleId = article.getId();

		List<FragmentSaveRequest> fragmentSaveRequests = saveRequest.getFragments();
		LOG.debug("Saving fragments: {}", fragmentSaveRequests);
		for (FragmentSaveRequest fragmentSaveRequest : fragmentSaveRequests) {
			Fragment fragment = fragmentService.save(fragmentSaveRequest, articleId);
			article.addFragment(fragment);
		}

		List<ArticleSourceSaveRequest> articleSourceSaveRequests = saveRequest.getSources();
		LOG.debug("Sorting article sources by order: {}", articleSourceSaveRequests);
		articleSourceSaveRequests.sort((source1, source2) -> Integer.compare(source1.getOrder(), source2.getOrder()));

		LOG.debug("Saving sources: {}", articleSourceSaveRequests);
		for (ArticleSourceSaveRequest articleSourceSaveRequest : articleSourceSaveRequests) {
			ArticleSource articleSource = articleSourceService.save(articleSourceSaveRequest, articleId);
			article.addSource(articleSource);
		}

		LOG.debug("The params are OK: {}", saveRequest);

		article = repository.save(article);
		LOG.info("The article was saved successfully: {}", article);

		return article;
	}

	@Override
	public void update(ArticleUpdateRequest updateRequest, Long articleId)
			throws ValidationException, ArticleNotFoundException, CategoryNotFoundException {
		LOG.debug("Validating article: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "article");
		validator.validate(updateRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The data is invalid. Article: %s. Article id: %s. Result: %s", result,
					updateRequest, articleId, result);
		}

		Article article = findById(articleId);

		String title = updateRequest.getTitle();
		if (!title.equals(article.getTitle())) {
			LOG.debug("Setting title '{}' of article '{}'", title, articleId);
			article.setTitle(title);
		}

		Category category = article.getCategory();
		Long categoryId = updateRequest.getCategoryId();
		if (!category.getId().equals(categoryId)) {
			Category newCategory = categoryService.findById(categoryId);
			LOG.debug("Setting category from '{}' to '{}' of article '{}'", category.getId(), categoryId, articleId);
			article.setCategory(category);
		}

		repository.save(article);
		LOG.info("The article was updated successfully: {}", article);
	}

}
