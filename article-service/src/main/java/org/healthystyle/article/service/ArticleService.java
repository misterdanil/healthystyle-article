package org.healthystyle.article.service;

import java.time.Instant;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.service.dto.ArticleSaveRequest;
import org.healthystyle.article.service.dto.ArticleUpdateRequest;
import org.healthystyle.article.service.dto.PeriodSort;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CategoryNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
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
			"findMostWatched", String.class, Long.class, Long.class, PeriodSort.class, int.class, int.class);

	static final String[] FIND_MOST_MARKED_PARAM_NAMES = MethodNameHelper.getMethodParamNames(ArticleService.class,
			"findMostMarked", String.class, Long.class, Long.class, PeriodSort.class, int.class, int.class);

	Article findById(Long id) throws ValidationException, ArticleNotFoundException;

	Page<Article> find(int page, int limit) throws ValidationException;

	Page<Article> findByTitle(String title, int page, int limit) throws ValidationException;

	Page<Article> findByCategory(Long categoryId, int page, int limit) throws ValidationException;

	Page<Article> findByAuthor(Long author, int page, int limit) throws ValidationException;

	Page<Article> findMostWatched(String title, Long categoryId, Long authorId, PeriodSort periodSort, int page,
			int limit) throws ValidationException;

	Page<Article> findMostMarked(String title, Long categoryId, Long authorId, PeriodSort periodSort, int page,
			int limit) throws ValidationException;

	Article save(ArticleSaveRequest saveRequest, Long categoryId)
			throws ValidationException, ImageNotFoundException, ArticleNotFoundException, OrderExistException,
			PreviousOrderNotFoundException, FragmentExistException, ArticleLinkExistException, RollNotFoundException,
			FragmentNotFoundException, CategoryNotFoundException, TextNotFoundException;

	void update(ArticleUpdateRequest updateRequest, Long articleId)
			throws ValidationException, ArticleNotFoundException, CategoryNotFoundException;
}
