package org.healthystyle.article.service;

import java.util.List;

import org.healthystyle.article.model.Comment;
import org.healthystyle.article.service.dto.CommentSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CommentNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
	Comment findById(Long id) throws ValidationException, CommentNotFoundException;

	Page<Comment> findByUserId(Long userId, Pageable pageable);

	Page<Comment> findRootsByArticle(Long articleId, int page, int limit) throws ValidationException;

	List<Comment> findRepliesByComment(Long commentId, int page, int limit) throws ValidationException;

	Comment save(CommentSaveRequest saveRequest, Long articleId)
			throws ValidationException, ArticleNotFoundException, CommentNotFoundException;
}
