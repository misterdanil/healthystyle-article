package org.healthystyle.article.service.impl;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.View;
import org.healthystyle.article.repository.ViewRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.UserAccessor;
import org.healthystyle.article.service.ViewService;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.helper.IPHelper;
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
	@Autowired
	private IPHelper ipHelper;

	@Override
	public Integer countByArticle(Long articleId) {
		Integer count = repository.countByArticle(articleId);
		if (count == null) {
			return 0;
		}

		return count;
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
	public View checkAndSave(Long articleId) throws ValidationException, ArticleNotFoundException {
		User user = userAccessor.getUser();
		Article article = articleService.findById(articleId);
		if (user != null) {
			if (!repository.existsByUserIdAndArticle(user.getId(), articleId)) {
				View view = new View(user.getId(), article);
				return repository.save(view);
			}
			return null;
		}
		String ip = ipHelper.getIP();
		if (ip != null && !repository.existsByIpAndArticle(ip, articleId)) {
			View view = new View(ip, article);
			return repository.save(view);
		}

		return null;
	}

}
