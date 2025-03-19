package org.healthystyle.article.service.mark;

import org.healthystyle.article.model.mark.MarkArticle;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.mark.MarkNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;

public interface MarkArticleService {
	Float getAvgByArticle(Long articleId);

	Page<MarkArticle> findByArticle(Long articleId, int page, int limit);

	Page<MarkArticle> findByArticleAndValue(Long articleId, int page, int limit);

	Page<MarkArticle> findByUserId(Long userId, int oage, int limit);

	MarkArticle findByUserIdAndArticle(Long userId, Long articleId);

	Page<MarkArticle> findByUserIdAndValue(Long userId, Integer value, int page, int limit);

	MarkArticle save(Integer value, Long articleId)
			throws MarkNotFoundException, ValidationException, ArticleNotFoundException;

}
