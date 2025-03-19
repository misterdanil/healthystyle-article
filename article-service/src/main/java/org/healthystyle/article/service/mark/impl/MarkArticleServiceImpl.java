package org.healthystyle.article.service.mark.impl;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.mark.Mark;
import org.healthystyle.article.model.mark.MarkArticle;
import org.healthystyle.article.repository.mark.MarkArticleRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.UserAccessor;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.mark.MarkNotFoundException;
import org.healthystyle.article.service.mark.MarkArticleService;
import org.healthystyle.article.service.mark.MarkService;
import org.healthystyle.util.error.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MarkArticleServiceImpl implements MarkArticleService {
	@Autowired
	private MarkArticleRepository repository;
	@Autowired
	private UserAccessor userAccessor;
	@Autowired
	private MarkService markService;
	@Autowired
	private ArticleService articleService;

	@Override
	public Float getAvgByArticle(Long articleId) {
		return repository.getAvgByArticle(articleId);
	}

	@Override
	public Page<MarkArticle> findByArticle(Long articleId, int page, int limit) {
		return repository.findByArticle(articleId, PageRequest.of(page, limit));
	}

	@Override
	public Page<MarkArticle> findByArticleAndValue(Long articleId, int page, int limit) {
		return repository.findByArticleAndValue(articleId, PageRequest.of(page, limit));
	}

	@Override
	public Page<MarkArticle> findByUserId(Long userId, int page, int limit) {
		return repository.findByUserId(userId, PageRequest.of(page, limit));
	}

	@Override
	public MarkArticle findByUserIdAndArticle(Long userId, Long articleId) {
		return repository.findByUserIdAndArticle(userId, articleId);
	}

	@Override
	public Page<MarkArticle> findByUserIdAndValue(Long userId, Integer value, int page, int limit) {
		return repository.findByUserIdAndValue(userId, value, PageRequest.of(page, limit));
	}

	@Override
	public MarkArticle save(Integer value, Long articleId)
			throws MarkNotFoundException, ValidationException, ArticleNotFoundException {
		Mark mark = markService.findByValue(value);
		Article article = articleService.findById(articleId);
		MarkArticle markArticle = new MarkArticle(article, mark, userAccessor.getUser().getId());

		return repository.save(markArticle);
	}

}
