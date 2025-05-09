package org.healthystyle.article.web.mark;

import java.net.URI;
import java.net.URISyntaxException;

import org.healthystyle.article.model.mark.MarkArticle;
import org.healthystyle.article.service.dto.mark.MarkSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.mark.MarkNotFoundException;
import org.healthystyle.article.service.mark.MarkArticleService;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarkArticleController {
	@Autowired
	private MarkArticleService service;

	private static final Logger LOG = LoggerFactory.getLogger(MarkArticleController.class);

	@PostMapping("/articles/{id}/mark")
	public ResponseEntity<?> markArticle(@PathVariable Long id, @RequestBody MarkSaveRequest saveRequest)
			throws URISyntaxException {
		LOG.debug("Got request to rate article '{}': {}", id, saveRequest);

		MarkArticle mark;
		try {
			mark = service.save(saveRequest.getValue(), id);
		} catch (ArticleNotFoundException | MarkNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (ValidationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("1001", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.created(new URI("/marks/" + mark.getId())).build();
	}
}
