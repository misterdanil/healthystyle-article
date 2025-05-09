package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.service.dto.fragment.OrderSaveRequest;
import org.healthystyle.article.service.dto.fragment.OrderUpdateRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.OrderNotFoundException;
import org.healthystyle.article.service.error.fragment.image.FragmentImageNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkNotFoundException;
import org.healthystyle.article.service.error.fragment.quote.QuoteNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.service.util.MethodNameHelper;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;

public interface OrderService {
	static final String[] FIND_BY_FRAGMENT_AND_ORDER_PARAM_NAMES = MethodNameHelper
			.getMethodParamNames(OrderService.class, "findByFragmentAndOrder", Long.class, Integer.class);

	static final String[] FIND_BY_FRAGMENT_PARAM_NAMES = MethodNameHelper.getMethodParamNames(OrderService.class,
			"findByFragment", Long.class, int.class, int.class);

	Order findById(Long id) throws ValidationException, OrderNotFoundException;

	Order findByFragmentAndOrder(Long fragmentId, Integer order) throws ValidationException, OrderNotFoundException;

	Page<Order> findByFragment(Long fragmentId, int page, int limit) throws ValidationException;

	Order save(OrderSaveRequest saveRequest, Long fragmentId) throws ValidationException, OrderExistException,
			PreviousOrderNotFoundException, ArticleNotFoundException, ArticleLinkExistException, ImageNotFoundException,
			RollNotFoundException, FragmentNotFoundException, TextNotFoundException;

	void update(OrderUpdateRequest updateRequest, Long orderId)
			throws ValidationException, OrderNotFoundException, ArticleLinkNotFoundException, ArticleLinkExistException,
			ArticleNotFoundException, FragmentImageNotFoundException, ImageNotFoundException, QuoteNotFoundException,
			PreviousOrderNotFoundException;
}
