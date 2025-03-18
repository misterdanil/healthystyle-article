package org.healthystyle.article.service.error.fragment.link;

import java.util.Arrays;

import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.Field;
import org.springframework.validation.BindingResult;

public class ArticleLinkExistException extends AbstractException {
	private Field<?>[] fields;

	public ArticleLinkExistException(BindingResult result, Field<?>... fields) {
		super(result, "An article has already existed by fields: {}", Arrays.asList(fields));
		this.fields = fields;
	}

	public Field<?>[] getFields() {
		return fields;
	}

}
