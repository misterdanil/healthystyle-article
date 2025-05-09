package org.healthystyle.article.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.Comment;
import org.healthystyle.article.repository.CommentRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.CommentService;
import org.healthystyle.article.service.UserAccessor;
import org.healthystyle.article.service.dto.CommentSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CommentNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.healthystyle.util.user.User;
import org.healthystyle.util.validation.ParamsChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository repository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private Validator validator;
	@Autowired
	private UserAccessor userAccessor;

	private static final Integer MAX_SIZE = 25;

	private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Override
	public Comment findById(Long id) throws ValidationException, CommentNotFoundException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "comment");

		LOG.debug("Check id for not null: {}", id);
		if (id == null) {
			result.reject("comment.find.id.not_null", "Укажите идентификатор комментария для поиска");
			throw new ValidationException("Id is null", result);
		}

		Optional<Comment> comment = repository.findById(id);
		if (comment.isEmpty()) {
			result.reject("comment.find.not_found", "Не удалось найти комментарий");
			throw new CommentNotFoundException(id, result);
		}

		LOG.info("Got comment by id '{}' successfully", id);

		return comment.get();
	}

	@Override
	public Page<Comment> findByUserId(Long userId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Comment> findRootsByArticle(Long articleId, int page, int limit) throws ValidationException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "comment");
		if (articleId == null) {
			result.reject("comment.find_article_id.not_null", "Укажите идентификатор статьи для поиска");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid", result);
		}

		Page<Comment> comments = repository.findRootsByArticle(articleId, PageRequest.of(page, limit));

		LOG.info("Got comments successfully by params: {}", "stub");

		return comments;
	}

	@Override
	public List<Comment> findRepliesByComment(Long commentId, int page, int limit) throws ValidationException {
		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "comment");
		if (commentId == null) {
			result.reject("comment.find_replies.comment_id.not_null", "Укажите идентификатор комментария для поиска");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid", result);
		}

		List<Comment> comments = repository.findRepliesByComment(commentId, page, limit);

		LOG.info("Got comments successfully by params: {}", "stub");

		return comments;
	}

	@Override
	public Comment save(CommentSaveRequest saveRequest, Long articleId)
			throws ValidationException, ArticleNotFoundException, CommentNotFoundException {
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "comment");
		LOG.debug("Validating comment: {}", saveRequest);
		validator.validate(saveRequest, result);
		if (articleId == null) {
			result.reject("comment.save.article_id_not_null", "Укажите идентификатор статьи");
		}
		if (result.hasErrors()) {
			throw new ValidationException("The comment is invalid: %s. Result: %s", result, saveRequest, result);
		}

		LOG.debug("The comment is OK: {}", saveRequest);

		Article article = articleService.findById(articleId);
		User user = userAccessor.getUser();

		String text = saveRequest.getText();
		Long replyTo = saveRequest.getReplyTo();

		Comment comment;
		if (replyTo == null) {
			comment = new Comment(text, user.getId(), article);
		} else {
			Comment replyToComment = findById(replyTo);
			comment = new Comment(text, user.getId(), article, replyToComment);
		}

		comment = repository.save(comment);

		LOG.info("Comment was saved successfully: {}", comment);

		return comment;
	}

}
