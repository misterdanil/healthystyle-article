package org.healthystyle.article.service.dto.fragment;

import jakarta.validation.constraints.NotNull;

public class ArticleLinkUpdateRequest extends OrderUpdateRequest{
	@NotNull(message = "Укажите статью в качестве ссылки")
	private Long articleId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

}
