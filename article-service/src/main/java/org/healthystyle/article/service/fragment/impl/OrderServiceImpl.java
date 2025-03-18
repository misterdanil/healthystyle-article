package org.healthystyle.article.service.fragment.impl;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.repository.fragment.OrderRepository;
import org.healthystyle.article.service.dto.fragment.ArticleLinkSaveRequest;
import org.healthystyle.article.service.dto.fragment.ArticleLinkUpdateRequest;
import org.healthystyle.article.service.dto.fragment.FragmentImageSaveRequest;
import org.healthystyle.article.service.dto.fragment.FragmentImageUpdateRequest;
import org.healthystyle.article.service.dto.fragment.OrderSaveRequest;
import org.healthystyle.article.service.dto.fragment.OrderUpdateRequest;
import org.healthystyle.article.service.dto.fragment.QuoteSaveRequest;
import org.healthystyle.article.service.dto.fragment.QuoteUpdateRequest;
import org.healthystyle.article.service.dto.fragment.RollSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.OrderField;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentIdField;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.OrderNotFoundException;
import org.healthystyle.article.service.error.fragment.image.FragmentImageNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkNotFoundException;
import org.healthystyle.article.service.error.fragment.quote.QuoteNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.fragment.ArticleLinkService;
import org.healthystyle.article.service.fragment.FragmentImageService;
import org.healthystyle.article.service.fragment.FragmentService;
import org.healthystyle.article.service.fragment.OrderService;
import org.healthystyle.article.service.fragment.QuoteService;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository repository;
	@Autowired
	private FragmentService fragmentService;
	@Autowired
	private Validator validator;
	@Autowired
	private ArticleLinkService articleLinkService;
	@Autowired
	private FragmentImageService fragmentImageService;
	@Autowired
	private QuoteService quoteService;
	@Autowired
	private RollService rollService;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public Order findById(Long id) throws ValidationException, OrderNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "order");
		LOG.debug("Checking id for not null: {}", id);
		if (id == null) {
			result.reject("order.find.id.not_null", "Укажите идентификатор порядка для поиска");
			throw new ValidationException("The id is null", result);
		}

		Optional<Order> order = repository.findById(id);
		LOG.debug("Checking order for existence by id '{}'", id);
		if (order.isEmpty()) {
			result.reject("order.find.id.not_found", "Не удалось найти порядок по заданному идентификатору");
			throw new OrderNotFoundException(result, new IdField(id));
		}
		LOG.info("Got order successfully by id '{}'", id);

		return order.get();
	}

	@Override
	public Order findByFragmentAndOrder(Long fragmentId, Integer order)
			throws ValidationException, OrderNotFoundException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_FRAGMENT_AND_ORDER_PARAM_NAMES, fragmentId, order);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "order");

		LOG.debug("Checking fragment id for not null: {}", fragmentId);
		if (fragmentId == null) {
			result.reject("order.find.fragment_id.not_null", "Укажите идентификатор фрагмента для поиска");
		}

		LOG.debug("Checking order for not null: {}", order);
		if (order == null) {
			result.reject("order.find.order.not_null", "Укажите порядок для поиска");
		}

		LOG.debug("Checking errors for existence: {}", result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		Order foundedOrder = repository.findByFragmentAndOrder(fragmentId, order);
		LOG.debug("Checking order for existence: {}", foundedOrder);
		if (foundedOrder == null) {
			result.reject("order.find.not_found", "Не удалось найти часть фрагмента");
			throw new OrderNotFoundException(result, new FragmentIdField(fragmentId), new OrderField(order));
		}
		LOG.info("The order was found successfully by fragment id '{}' and order '{}'", fragmentId, order);

		return foundedOrder;
	}

	@Override
	public Page<Order> findByFragment(Long fragmentId, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_FRAGMENT_PARAM_NAMES, fragmentId, page, limit);

		LOG.debug("Validating params: {}", params);
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "order");

		LOG.debug("Checking fragment id for not null: {}", fragmentId);
		if (fragmentId == null) {
			result.reject("order.find.fragment_id.not_null", "Укажите идентификатор фрагмента для поиска");
		}

		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);

		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		Page<Order> orders = repository.findByFragment(fragmentId, PageRequest.of(page, limit));
		LOG.info("Orders was found successfully by params: {}", params);

		return orders;
	}

	@Override
	public Order save(OrderSaveRequest saveRequest, Long fragmentId)
			throws ValidationException, OrderExistException, PreviousOrderNotFoundException, ArticleNotFoundException,
			ArticleLinkExistException, ImageNotFoundException, RollNotFoundException, FragmentNotFoundException {
		LOG.debug("Validating order: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "order");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The order is invalid: %s. Result: %s", result, saveRequest, result);
		}

		LOG.debug("Checking fragment existence by id '{}' to save order '{}'", fragmentId, saveRequest);
		Fragment fragment = fragmentService.findById(fragmentId);

		Integer order = saveRequest.getOrder();
		LOG.debug("Checking order for existence '{}'", order);
		if (repository.existsByFragmentAndOrder(fragmentId, order)) {
			result.rejectValue("order", "order.save.order.exists", "Данный порядок у фрагмента уже существует");
			throw new OrderExistException(order, result);
		}
		if (order > 1 && !repository.existsByFragmentAndOrder(fragmentId, order - 1)) {
			result.rejectValue("order", "order.save.order.previous_not_found", "Прошлый порядок не был найден");
			throw new PreviousOrderNotFoundException(order, result);
		}

		LOG.debug("The order is OK: {}", saveRequest);
		
		Order savedOrder;
		if (saveRequest instanceof ArticleLinkSaveRequest) {
			savedOrder = articleLinkService.save((ArticleLinkSaveRequest) saveRequest, fragment);
		} else if (saveRequest instanceof FragmentImageSaveRequest) {
			savedOrder = fragmentImageService.save((FragmentImageSaveRequest) saveRequest, fragment);
		} else if (saveRequest instanceof QuoteSaveRequest) {
			savedOrder = quoteService.save((QuoteSaveRequest) saveRequest, fragment);
		} else if (saveRequest instanceof RollSaveRequest) {
			savedOrder = rollService.save((RollSaveRequest) saveRequest, fragment);
		}
		else {
			throw new RuntimeException("Unknown type: " + saveRequest.getClass().getName());
		}
		
		return savedOrder;

	}

	@Override
	public void update(OrderUpdateRequest updateRequest, Long orderId)
			throws ValidationException, OrderNotFoundException, ArticleLinkNotFoundException, ArticleLinkExistException,
			ArticleNotFoundException, FragmentImageNotFoundException, ImageNotFoundException, QuoteNotFoundException, PreviousOrderNotFoundException {
		LOG.debug("Validating order: {}", updateRequest);
		BindingResult result = new BeanPropertyBindingResult(updateRequest, "order");
		validator.validate(updateRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The order is invalid: %s. Result: %s", result, updateRequest, result);
		}

		LOG.debug("Checking order for existence by id '{}' to update order '{}'", orderId, updateRequest);
		Order order = findById(orderId);
		Long fragmentId = order.getFragment().getId();

		Integer newOrder = updateRequest.getOrder();
		if (newOrder != order.getOrder()) {
			LOG.debug("Checking order for existence '{}'", newOrder);
			try {
				Order currentOrder = findByFragmentAndOrder(fragmentId, newOrder);
				currentOrder.setOrder(order.getOrder());
				repository.save(currentOrder);
			} catch (OrderNotFoundException e) {
				LOG.debug("Checking existence for previous order: {}", newOrder);
				if (newOrder > 1 && !repository.existsByFragmentAndOrder(fragmentId, newOrder - 1)) {
					result.reject("order.update.order.previous_not_found", "Не удалось найти прошлый порядок");
					throw new PreviousOrderNotFoundException(newOrder, result);
				}
			}
			order.setOrder(newOrder);
		}

		LOG.debug("The order is OK: {}", updateRequest);
		if (updateRequest instanceof ArticleLinkUpdateRequest) {
			articleLinkService.update((ArticleLinkUpdateRequest) updateRequest, orderId);
		} else if (updateRequest instanceof FragmentImageUpdateRequest) {
			fragmentImageService.update((FragmentImageUpdateRequest) updateRequest, orderId);
		} else if (updateRequest instanceof QuoteUpdateRequest) {
			quoteService.update((QuoteUpdateRequest) updateRequest, orderId);
		}
	}

}
