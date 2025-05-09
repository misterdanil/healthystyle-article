package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;
import org.healthystyle.article.service.dto.fragment.FragmentUpdateRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
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

public interface FragmentService {
	static final String[] FIND_BY_ARTICLE_PARAM_NAMES = MethodNameHelper.getMethodParamNames(FragmentService.class,
			"findByArticle", Long.class, int.class, int.class);

	static final String[] FIND_BY_ARTICLE_AND_ORDER_PARAM_NAMES = MethodNameHelper
			.getMethodParamNames(FragmentService.class, "findByArticleAndOrder", Long.class, Integer.class);

	Fragment findById(Long id) throws ValidationException, FragmentNotFoundException;

	Fragment findByArticleAndOrder(Long articleId, Integer order) throws ValidationException, FragmentNotFoundException;

	Page<Fragment> findByArticle(Long articleId, int page, int limit) throws ValidationException;

	Fragment save(FragmentSaveRequest saveRequest, Long articleId) throws ValidationException, FragmentExistException,
			OrderExistException, PreviousOrderNotFoundException, ArticleNotFoundException, ArticleLinkExistException,
			ImageNotFoundException, RollNotFoundException, FragmentNotFoundException, TextNotFoundException;

	void update(FragmentUpdateRequest updateRequest, Long fragmentId) throws ValidationException,
			FragmentNotFoundException, PreviousOrderNotFoundException, FragmentExistException;

	void deleteById(Long id) throws ValidationException, FragmentNotFoundException;
}
