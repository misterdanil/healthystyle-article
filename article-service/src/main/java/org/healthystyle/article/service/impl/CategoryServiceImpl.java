package org.healthystyle.article.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.healthystyle.article.model.Category;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.repository.CategoryRepository;
import org.healthystyle.article.service.CategoryService;
import org.healthystyle.article.service.dto.CategorySaveRequest;
import org.healthystyle.article.service.dto.CategoryUpdateRequest;
import org.healthystyle.article.service.error.CategoryExistException;
import org.healthystyle.article.service.error.CategoryNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
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
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repository;
	@Autowired
	private Validator validator;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Override
	public Category findById(Long id) throws ValidationException, CategoryNotFoundException {
		BindingResult result = new MapBindingResult(new HashMap<>(), "category");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("category.find.id.not_null", "Укажите идентификатор категории для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking category for existence by id '{}'", id);
		Optional<Category> category = repository.findById(id);
		if (category.isEmpty()) {
			result.reject("category.find.not_found", "Не удалось найти категорию");
			throw new CategoryNotFoundException(id, result);
		}
		LOG.info("Got category successfully by id '{}'", id);

		return category.get();
	}

	@Override
	public Page<Category> findByTitle(String title, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_TITLE_PARAM_NAMES, title, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "category");

		LOG.debug("Checking title for not null: {}", title);
		if (title == null) {
			result.reject("category.find.title.not_null", "Укажите название категории для поиска");
		}

		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);

		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		Page<Category> categories = repository.findByTitle(title, PageRequest.of(page, limit));
		LOG.info("Categories was found successfully by params: {}", params);

		return categories;
	}

	@Override
	public List<Category> findAllRootCategories() {
		LOG.debug("Getting all root categories");
		List<Category> categories = repository.findAllRootCategories();

		return categories;
	}

	@Override
	public List<Category> findAllChildCategories(Long categoryId) {
		List<Category> categories = repository.findAllChildCategories(categoryId);

		return categories;
	}

	@Override
	public Category save(CategorySaveRequest saveRequest) throws ValidationException, CategoryExistException,
			CategoryNotFoundException, OrderExistException, PreviousOrderNotFoundException {
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "category");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The category is invalid: %s", result, saveRequest);
		}

		String title = saveRequest.getTitle();
		if (repository.existsByTitle(title)) {
			result.rejectValue("title", "category.save.title.exists", "Категория с таким названием уже существует");
			throw new CategoryExistException(title, result);
		}

		Long parentCategoryId = saveRequest.getParentCategoryId();
		Category parentCategory = null;
		if (parentCategoryId != null) {
			parentCategory = findById(parentCategoryId);
		}

		Integer order = saveRequest.getOrder();
		LOG.debug("Checking order for existence '{}'", order);
		if (parentCategoryId != null) {
			if (repository.existsByParentAndOrder(parentCategoryId, order)) {
				result.rejectValue("order", "order.save.order.exists", "Данный порядок у фрагмента уже существует");
				throw new OrderExistException(order, result);
			}

			if (order > 1 && !repository.existsByParentAndOrder(parentCategoryId, order - 1)) {
				result.rejectValue("order", "order.save.order.previous_not_found", "Прошлый порядок не был найден");
				throw new PreviousOrderNotFoundException(order, result);
			}
		} else {
			if (repository.existsByOrder(order)) {
				result.rejectValue("order", "order.save.order.exists", "Данный порядок у фрагмента уже существует");
				throw new OrderExistException(order, result);
			}

			if (order > 1 && !repository.existsByOrder(order - 1)) {
				result.rejectValue("order", "order.save.order.previous_not_found", "Прошлый порядок не был найден");
				throw new PreviousOrderNotFoundException(order, result);
			}
		}

		LOG.debug("The params are OK: {}", saveRequest);

		Category category = new Category(title, order);
		if (parentCategory != null) {
			category.setParentCategory(parentCategory);
		}

		category = repository.save(category);

		return category;
	}

	@Override
	public void update(CategoryUpdateRequest updateRequest, Long categoryId) {
//		LOG.debug("Validating category: {}", updateRequest);
//		BindingResult result = new BeanPropertyBindingResult(updateRequest, "category");
//		validator.validate(updateRequest, result);
//		if (result.hasErrors()) {
//			throw new ValidationException("The category is invalid: %s. Result: %s", result, updateRequest, result);
//		}
//
//		LOG.debug("Checking category for existence by id '{}' to update it '{}'", categoryId, updateRequest);
//		Category category = findById(categoryId);
//
//		Integer newOrder = updateRequest.getOrder();
//		if (newOrder != category.getOrder()) {
//			LOG.debug("Checking order for existence '{}'", newOrder);
//			try {
//				Fragment currentFragment = findByArticleAndOrder(articleId, newOrder);
//				currentFragment.setOrder(fragment.getOrder());
//				repository.save(currentFragment);
//			} catch (FragmentNotFoundException e) {
//				LOG.debug("Checking existence for previous order: {}", newOrder);
//				if (newOrder > 1 && !repository.existsByArticleAndOrder(fragmentId, newOrder - 1)) {
//					result.reject("fragment.update.order.previous_not_found", "Не удалось найти прошлый порядок");
//					throw new PreviousOrderNotFoundException(newOrder, result);
//				}
//			}
//			fragment.setOrder(newOrder);
//		}
//
//		String title = updateRequest.getTitle();
//		if (!title.equals(fragment.getTitle())) {
//			LOG.debug("Checking article id '{}' and title '{}' for existence", articleId, title);
//			if (repository.existsByArticleAndTitle(articleId, title)) {
//				result.reject("fragment.update.title.exists", "Фрагмент с таким заголовком уже существует");
//				throw new FragmentExistException(articleId, title, result);
//			}
//			fragment.setTitle(title);
//		}
//
//		LOG.debug("The fragment is OK: {}", updateRequest);
//
//		repository.save(fragment);
//		LOG.info("The fragment was updated successfully");
	}

}
