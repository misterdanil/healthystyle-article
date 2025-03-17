package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.service.dto.fragment.OrderSaveRequest;

public interface OrderService {
	Order save(OrderSaveRequest saveRequest, Long fragmentId);
}
