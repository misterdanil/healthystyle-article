package org.healthystyle.article.web;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.service.fragment.FragmentService;
import org.healthystyle.article.web.dto.mapper.FragmentMapper;
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
public class FragmentController {
	@Autowired
	private FragmentService service;
	@Autowired
	private FragmentMapper mapper;

	private static final Logger LOG = LoggerFactory.getLogger(FragmentController.class);

	@GetMapping("/articles/{id}/fragments")
	public ResponseEntity<?> getFragmentsByArticleId(@PathVariable Long id, @RequestParam int page,
			@RequestParam int limit) {
		LOG.debug("Got request to get fragments by article id: {}", id);

		Page<Fragment> fragments;
		try {
			fragments = service.findByArticle(id, page, limit);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(fragments.map(mapper::toDto));
	}
}
