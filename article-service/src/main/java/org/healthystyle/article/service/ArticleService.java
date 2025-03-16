package org.healthystyle.article.service;

import java.time.Instant;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.service.dto.ArticleSaveRequest;
import org.healthystyle.article.service.dto.ArticleUpdateRequest;
import org.healthystyle.article.service.util.MethodNameHelper;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;

public interface ArticleService {
	static final String[] FIND_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleService.class, "find",
			int.class, int.class);

	static final String[] FIND_BY_TITLE_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleService.class,
			"findByTitle", String.class, int.class, int.class);

	static final String[] FIND_BY_CATEGORY_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleService.class,
			"findByCategory", Long.class, int.class, int.class);

	static final String[] FIND_BY_AUTHOR_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleService.class,
			"findByAuthor", Long.class, int.class, int.class);

	static final String[] FIND_MOST_WATCHED_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleService.class,
			"findMostWatched", Long.class, Long.class, Instant.class, Instant.class, int.class, int.class);

	static final String[] FIND_MOST_MARKED_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleService.class,
			"findMostMarked", Long.class, Long.class, Instant.class, Instant.class, int.class, int.class);

	Page<Article> find(int page, int limit) throws ValidationException;

	Page<Article> findByTitle(String title, int page, int limit) throws ValidationException;

	Page<Article> findByCategory(Long categoryId, int page, int limit) throws ValidationException;

	Page<Article> findByAuthor(Long author, int page, int limit) throws ValidationException;

	Page<Article> findMostWatched(Long categoryId, Long authorId, Instant start, Instant end, int page, int limit)
			throws ValidationException;

	Page<Article> findMostMarked(Long categoryId, Long authorId, Instant start, Instant end, int page, int limit)
			throws ValidationException;

	Article save(ArticleSaveRequest saveRequest, Long categoryId) throws ValidationException;

	void update(ArticleUpdateRequest updateRequest, Long articleId) throws ValidationException;

}
