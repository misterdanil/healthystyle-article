package org.healthystyle.article.service;

import org.healthystyle.article.model.ArticleSource;
import org.healthystyle.article.service.dto.ArticleSourceSaveRequest;
import org.healthystyle.article.service.dto.ArticleSourceUpdateRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.ArticleSourceNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.util.MethodNameHelper;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;

public interface ArticleSourceService {
	static final String[] FIND_BY_ARTICLE_AND_ORDER_PARAM_NAMES = MethodNameHelper
			.getMethodParamNames(ArticleSourceService.class, "findByArticleAndOrder", Long.class, Integer.class);

	static final String[] FIND_BY_ARTICLE_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleSourceService.class,
			"findByArticle", Long.class, int.class, int.class);

	ArticleSource findById(Long id) throws ValidationException, ArticleSourceNotFoundException;

	ArticleSource findByArticleAndOrder(Long id, Integer order)
			throws ValidationException, ArticleSourceNotFoundException;

	ArticleSource save(ArticleSourceSaveRequest saveRequest, Long articleId)
			throws ValidationException, ArticleNotFoundException, OrderExistException, PreviousOrderNotFoundException;

	Page<ArticleSource> findByArticle(Long articleId, int page, int limit) throws ValidationException;

	void update(ArticleSourceUpdateRequest updateRequest, Long articleSourceId)
			throws ValidationException, PreviousOrderNotFoundException, ArticleSourceNotFoundException;
}
