package org.healthystyle.article.service.impl;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.View;
import org.healthystyle.article.repository.ViewRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.UserAccessor;
import org.healthystyle.article.service.ViewService;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.healthystyle.util.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ViewServiceImpl implements ViewService {
	@Autowired
	private ViewRepository repository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserAccessor userAccessor;

	@Override
	public Integer countByArticle(Long articleId) {
		return repository.countByArticle(articleId);
	}

	@Override
	public Page<View> findByUserId(Long userId, int page, int limit) {
		return repository.findByUserId(userId, PageRequest.of(page, limit));
	}

	@Override
	public Page<View> findByArticle(Long articleId, int page, int limit) {
		return repository.findByArticle(articleId, PageRequest.of(page, limit));
	}

	@Override
	public View save(Long articleId) throws ValidationException, ArticleNotFoundException {
		User user = userAccessor.getUser();
		Article article = articleService.findById(articleId);
		if (user != null && !repository.existsByUserIdAndArticle(user.getId(), articleId)) {
			View view = new View(user.getId(), article);
			return repository.save(view);
		} else {
			return null;
		}
	}

}
