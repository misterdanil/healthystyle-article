package org.healthystyle.article.service.error;

import java.util.Arrays;

import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.Field;
import org.springframework.validation.BindingResult;

public class ArticleSourceNotFoundException extends AbstractException {
	private Field<?>[] fields;

	public ArticleSourceNotFoundException(BindingResult result, Field<?>... fields) {
		super(result, "Could not found article source. Fields: '%s'", Arrays.asList(fields));
		this.fields = fields;
	}

	public Field<?>[] getFields() {
		return fields;
	}

}
