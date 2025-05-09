package org.healthystyle.article.web;

import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.service.fragment.OrderService;
import org.healthystyle.article.web.dto.mapper.OrderMapper;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
	@Autowired
	private OrderService service;
	@Autowired
	private OrderMapper mapper;

	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@GetMapping("/fragments/{fragmentId}/orders")
	public ResponseEntity<?> getOrdersByFragmentId(@PathVariable Long fragmentId, @RequestParam int page,
			@RequestParam int limit) {
		LOG.debug("Got request to get orders by fragment id: {}", fragmentId);

		Page<Order> orders;
		try {
			orders = service.findByFragment(fragmentId, page, limit);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(orders.map(mapper::toDto));
	}
}
