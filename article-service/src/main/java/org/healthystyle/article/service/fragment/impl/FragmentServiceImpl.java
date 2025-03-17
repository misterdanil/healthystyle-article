package org.healthystyle.article.service.fragment.impl;

import java.util.List;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.repository.fragment.FragmentRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;
import org.healthystyle.article.service.dto.fragment.OrderSaveRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.fragment.FragmentService;
import org.healthystyle.article.service.fragment.OrderService;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
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
	private final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(FragmentServiceImpl.class);

	@Override
	public Page<Fragment> findByArticle(Long articleId, Pageable pageable) {
	}

	@Override
	public Fragment save(FragmentSaveRequest saveRequest, Long articleId)
			throws ValidationException, FragmentExistException, OrderExistException, PreviousOrderNotFoundException {
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
		}

		Integer order = saveRequest.getOrder();
		LOG.debug("Checking order for existence '{}'", order);
		if (repository.existsByArticleAndOrder(articleId, order)) {
			result.rejectValue("order", "fragment.save.order.exists",
					"Фрамент с таким порядком уже существует у этой статьи");
			throw new OrderExistException(order, result);
		}

		LOG.debug("Checking previous order for existence '{}'", order);
		if (order > 1 && repository.existsByArticleAndOrder(articleId, order - 1)) {
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

}
