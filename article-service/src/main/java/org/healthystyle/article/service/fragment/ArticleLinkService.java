package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.ArticleLink;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.service.dto.fragment.ArticleLinkSaveRequest;
import org.healthystyle.article.service.dto.fragment.ArticleLinkUpdateRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface ArticleLinkService {
	ArticleLink findById(Long id) throws ValidationException, ArticleLinkNotFoundException;

	ArticleLink save(ArticleLinkSaveRequest saveRequest, Fragment fragment)
			throws ValidationException, ArticleNotFoundException, ArticleLinkExistException;

	void update(ArticleLinkUpdateRequest updateRequest, Long articleLinkId) throws ValidationException,
			ArticleLinkNotFoundException, ArticleLinkExistException, ArticleNotFoundException;
}
