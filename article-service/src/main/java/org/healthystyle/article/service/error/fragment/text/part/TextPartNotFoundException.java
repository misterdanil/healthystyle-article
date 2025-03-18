package org.healthystyle.article.service.error.fragment.text.part;

import java.util.Arrays;

import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.Field;
import org.springframework.validation.BindingResult;

public class TextPartNotFoundException extends AbstractException {
	private Field<?>[] fields;

	public TextPartNotFoundException(BindingResult result, Field<?>... fields) {
		super(result, "Could not found text part by fields: %s", Arrays.asList(fields));
		this.fields = fields;
	}

	public Field<?>[] getFields() {
		return fields;
	}

}
