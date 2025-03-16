package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.ArticleSource;
import org.healthystyle.article.service.dto.ArticleSourceSaveRequest;

public interface ArticleSourceService {
	ArticleSource save(ArticleSourceSaveRequest saveRequest, Long articleId);
}
