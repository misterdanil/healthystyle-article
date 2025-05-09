package org.healthystyle.article.web.dto.fragment;

public class ArticleLinkDto extends OrderDto {
	private Long articleId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	@Override
	public String getType() {
		return "link";
	}

}
