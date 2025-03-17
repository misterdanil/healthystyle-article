package org.healthystyle.article.service.error;

import org.healthystyle.util.error.Field;

public class ArticleIdField extends Field<Long> {

	public ArticleIdField(Long value) {
		super(value);
	}

	@Override
	public String getName() {
		return "article id";
	}

}
