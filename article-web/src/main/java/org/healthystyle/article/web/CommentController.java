package org.healthystyle.article.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.healthystyle.article.model.Comment;
import org.healthystyle.article.service.CommentService;
import org.healthystyle.article.service.dto.CommentSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CommentNotFoundException;
import org.healthystyle.article.web.dto.mapper.CommentMapper;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
	@Autowired
	private CommentService service;
	@Autowired
	private CommentMapper mapper;

	private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

	@GetMapping("/articles/{id}/comments")
	public ResponseEntity<?> getRootComments(@PathVariable Long id, @RequestParam int page, @RequestParam int limit) {
		LOG.debug("Got request to get root comments by article id: {}", id);

		Page<Comment> comments;
		try {
			comments = service.findRootsByArticle(id, page, limit);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(comments.map(mapper::toDto));
	}

	@GetMapping("/comments/{id}/replies")
	public ResponseEntity<?> getReplies(@PathVariable Long id, @RequestParam int page, @RequestParam int limit) {
		LOG.debug("Got request to get root comments by article id: {}", id);

		List<Comment> comments;
		try {
			comments = service.findRepliesByComment(id, page, limit);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(comments);
	}

	@PostMapping("/articles/{id}/comment")
	public ResponseEntity<?> addComment(@RequestBody CommentSaveRequest saveRequest, @PathVariable Long id) throws URISyntaxException {
		LOG.debug("Got request to save comment: {}", saveRequest);

		Comment comment;
		try {
			comment = service.save(saveRequest, id);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (CommentNotFoundException | ArticleNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("1001", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.created(new URI("/comments/" + comment.getId())).build();
	}
}
