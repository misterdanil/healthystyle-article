package org.healthystyle.article.service;

import org.healthystyle.article.model.View;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;

public interface ViewService {
	Integer countByArticle(Long articleId);

	Page<View> findByUserId(Long userId, int page, int limit);

	Page<View> findByArticle(Long articleId, int page, int limit);

	View save(Long articleId) throws ValidationException, ArticleNotFoundException;
}
