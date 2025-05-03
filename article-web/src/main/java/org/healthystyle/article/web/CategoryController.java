package org.healthystyle.article.web;

import org.healthystyle.article.model.Category;
import org.healthystyle.article.service.CategoryService;
import org.healthystyle.article.web.dto.mapper.CategoryMapper;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService service;
	@Autowired
	private CategoryMapper mapper;

	private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@GetMapping("/categories")
	public ResponseEntity<?> getByTitle(@RequestParam String title, @RequestParam int page, @RequestParam int limit) {
		LOG.debug("Got request to get categories");

		Page<Category> categories;
		try {
			categories = service.findByTitle(title, page, limit);
		} catch (ValidationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(categories.map(mapper::toDto));
	}
}
