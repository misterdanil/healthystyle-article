package org.healthystyle.article.service.fragment.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.repository.fragment.FragmentRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;
import org.healthystyle.article.service.dto.fragment.FragmentUpdateRequest;
import org.healthystyle.article.service.dto.fragment.OrderSaveRequest;
import org.healthystyle.article.service.error.ArticleIdField;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.OrderField;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.OrderNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.service.fragment.FragmentService;
import org.healthystyle.article.service.fragment.OrderService;
import org.healthystyle.article.service.util.LogTemplate;
import org.healthystyle.article.service.util.ParamsChecker;
import org.healthystyle.util.error.AbstractException;
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
public class FragmentServiceImpl implements FragmentService {
	@Autowired
	private FragmentRepository repository;
	@Autowired
	private Validator validator;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private OrderService orderService;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(FragmentServiceImpl.class);

	@Override
	public Fragment findById(Long id) throws ValidationException, FragmentNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "fragment");

		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("fragment.find.id.not_null", "Укажите идентификатор фрагмента для поиска");
			throw new ValidationException("The id is null", result);
		}

		LOG.debug("Checking fragment for existence by id '{}'", id);
		Optional<Fragment> fragment = repository.findById(id);
		if (fragment.isEmpty()) {
			result.reject("fragment.find.not_found", "Не удалось найти фрагмента");
			throw new FragmentNotFoundException(result, new IdField(id));
		}
		LOG.info("Got fragment successfully by id '{}'", id);

		return fragment.get();
	}

	@Override
	public Fragment findByArticleAndOrder(Long articleId, Integer order)
			throws ValidationException, FragmentNotFoundException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_ARTICLE_AND_ORDER_PARAM_NAMES, articleId, order);

		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "fragment");
		LOG.debug("Validating params: {}", params);
		if (articleId == null) {
			result.reject("fragment.find.article_id.not_null", "Укажите идентификатор статьи для поиска её фрагментов");
		}
		if (order == null) {
			result.reject("fragment.find.order.not_null", "Укажите порядок фрагмента для его поиска");
		}
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Fragment fragment = repository.findByArticleAndOrder(articleId, order);
		if (fragment == null) {
			result.reject("fragment.find.not_found", "Не удалось найти фрагмент");
			throw new FragmentNotFoundException(result, new ArticleIdField(articleId), new OrderField(order));
		}
		LOG.info("Got fragment successfully by params: {}", params);

		return fragment;
	}

	@Override
	public Page<Fragment> findByArticle(Long articleId, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_ARTICLE_PARAM_NAMES, articleId, page, limit);

		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "fragment");
		LOG.debug("Validating params: {}", params);
		if (articleId == null) {
			result.reject("fragment.find.article_id.not_null", "Укажите идентификатор статьи для поиска её фрагментов");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Fragment> fragments = repository.findByArticle(articleId, PageRequest.of(page, limit));
		LOG.info("Got fragments successfully by params: {}", params);

		return fragments;
	}

	@Override
	@Transactional(rollbackFor = { AbstractException.class, RuntimeException.class })
	public Fragment save(FragmentSaveRequest saveRequest, Long articleId)
			throws ValidationException, FragmentExistException, OrderExistException, PreviousOrderNotFoundException,
			ArticleNotFoundException, ArticleLinkExistException, ImageNotFoundException, RollNotFoundException,
			FragmentNotFoundException, TextNotFoundException {
		LOG.debug("Validating fragment: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "fragment");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The fragment is invalid: %s. Result: %s", result, saveRequest, result);
		}

		Article article = articleService.findById(articleId);

		String title = saveRequest.getTitle();
		if (title != null) {
			LOG.debug("Checking article '{}' for title existence '{}'", articleId, title);
			if (repository.existsByArticleAndTitle(articleId, title)) {
				result.rejectValue("title", "fragment.save.title.exists",
						"Фрагмент с таким названием в рамках этой статьи уже существует");
				throw new FragmentExistException(articleId, title, result);
			}
			article.setTitle(title);
		}

		Integer order = saveRequest.getOrder();
		LOG.debug("Checking order for existence '{}'", order);
		if (repository.existsByArticleAndOrder(articleId, order)) {
			result.rejectValue("order", "fragment.save.order.exists",
					"Фрамент с таким порядком уже существует у этой статьи");
			throw new OrderExistException(order, result);
		}

		LOG.debug("Checking previous order for existence '{}'", order);
		if (order > 1 && !repository.existsByArticleAndOrder(articleId, order - 1)) {
			result.rejectValue("order", "fragment.save.order.previous_not_found",
					"Не найден прошлый фрагмент по порядку");
			throw new PreviousOrderNotFoundException(order, result);

		}

		LOG.debug("The fragment is OK: {}", saveRequest);

		Fragment fragment = new Fragment(order, article, title);
		fragment = repository.save(fragment);

		List<OrderSaveRequest> orderSaveRequests = saveRequest.getOrders();
		LOG.debug("Sorting orders by order: {}", orderSaveRequests);
		orderSaveRequests.sort((order1, order2) -> Integer.compare(order1.getOrder(), order2.getOrder()));
		LOG.debug("Saving orders: {}", orderSaveRequests);
		for (OrderSaveRequest orderSaveRequest : orderSaveRequests) {
			LOG.debug("Saving order: {}", orderSaveRequest);
			Order orderedFragmentPart = orderService.save(orderSaveRequest, fragment.getId());
			fragment.addOrder(orderedFragmentPart);
		}

		LOG.info("The fragment was saved successfully: {}", fragment);

		return fragment;
	}

	@Override
	public void update(FragmentUpdateRequest updateRequest, Long fragmentId) throws ValidationException,
			FragmentNotFoundException, PreviousOrderNotFoundException, FragmentExistException {
		LOG.debug("Validating fragment: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "fragment");
		validator.validate(updateRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The fragment is invalid: %s. Result: %s", result, updateRequest, result);
		}

		LOG.debug("Checking fragment for existence by id '{}' to update it '{}'", fragmentId, updateRequest);
		Fragment fragment = findById(fragmentId);
		Long articleId = fragment.getArticle().getId();

		Integer newOrder = updateRequest.getOrder();
		if (newOrder != fragment.getOrder()) {
			LOG.debug("Checking order for existence '{}'", newOrder);
			try {
				Fragment currentFragment = findByArticleAndOrder(articleId, newOrder);
				currentFragment.setOrder(fragment.getOrder());
				repository.save(currentFragment);
			} catch (FragmentNotFoundException e) {
				LOG.debug("Checking existence for previous order: {}", newOrder);
				if (newOrder > 1 && !repository.existsByArticleAndOrder(fragmentId, newOrder - 1)) {
					result.reject("fragment.update.order.previous_not_found", "Не удалось найти прошлый порядок");
					throw new PreviousOrderNotFoundException(newOrder, result);
				}
			}
			fragment.setOrder(newOrder);
		}

		String title = updateRequest.getTitle();
		if (!title.equals(fragment.getTitle())) {
			LOG.debug("Checking article id '{}' and title '{}' for existence", articleId, title);
			if (repository.existsByArticleAndTitle(articleId, title)) {
				result.reject("fragment.update.title.exists", "Фрагмент с таким заголовком уже существует");
				throw new FragmentExistException(articleId, title, result);
			}
			fragment.setTitle(title);
		}

		LOG.debug("The fragment is OK: {}", updateRequest);

		repository.save(fragment);
		LOG.info("The fragment was updated successfully");
	}

	@Override
	public void deleteById(Long id) throws ValidationException, FragmentNotFoundException {
		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			BindingResult result = new MapBindingResult(new HashMap<>(), "fragment");
			result.reject("fragment.delete.id.not_null", "Укажите идентификатор фрагмента для удаления");
		}

		Fragment fragment = findById(id);
		Long articleId = fragment.getArticle().getId();
		Integer order = fragment.getOrder();
		LOG.debug("Deleting fragment and shifting all next back by article id '{}' and order '{}'", articleId, order);
		repository.delete(fragment);
		repository.shiftAllNextBack(articleId, order);
		LOG.info("The fragment was deleted successfully by id '{}'", id);
	}

}
