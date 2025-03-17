package org.healthystyle.article.service.error.fragment;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class FragmentExistException extends AbstractException {
	private Long articleId;
	private String title;

	public FragmentExistException(Long articleId, String title, BindingResult result) {
		super(result, "A fragment of article '%s' has already existed with title '%s'", articleId, title);
		this.articleId = articleId;
		this.title = title;
	}

	public Long getArticleId() {
		return articleId;
	}

	public String getTitle() {
		return title;
	}

}
