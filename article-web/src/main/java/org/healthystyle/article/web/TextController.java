package org.healthystyle.article.web;

import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.service.fragment.TextService;
import org.healthystyle.article.web.dto.mapper.text.TextMapper;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextController {
	@Autowired
	private TextService textService;
	@Autowired
	private TextMapper mapper;

	private static final Logger LOG = LoggerFactory.getLogger(TextController.class);

	@GetMapping(value = "/articles/{id}/texts", params = "first")
	public ResponseEntity<?> getFirstTextByArticleId(@PathVariable Long id) {
		LOG.debug("Got request to get first text by article id: {}", id);

		Text text;
		try {
			text = textService.findFirstByArticle(id);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (TextNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("1001", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(mapper.toDto(text));
	}
}
