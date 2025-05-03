package org.healthystyle.article.web;

import java.net.URI;
import java.net.URISyntaxException;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.dto.ArticleSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CategoryNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
	@Autowired
	private ArticleService service;

	private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	@PostMapping("/categories/{categoryId}/article")
	public ResponseEntity<?> addArticle(@PathVariable Long categoryId, @RequestBody ArticleSaveRequest saveRequest)
			throws URISyntaxException {
		LOG.debug("Got request to save article: {}", saveRequest);

		Article article;
		try {
			article = service.save(saveRequest, categoryId);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1001", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (CategoryNotFoundException | FragmentNotFoundException | RollNotFoundException
				| PreviousOrderNotFoundException | ArticleNotFoundException | ImageNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("1002", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (FragmentExistException | OrderExistException | ArticleLinkExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ErrorResponse("1003", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.created(new URI("/categories/" + categoryId + "/articles/" + article.getId())).build();
	}
}
