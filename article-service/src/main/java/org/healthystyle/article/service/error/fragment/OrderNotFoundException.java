package org.healthystyle.article.service.error.fragment;

import java.util.Arrays;

import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.Field;
import org.springframework.validation.BindingResult;

public class OrderNotFoundException extends AbstractException {
	private Field<?>[] fields;

	public OrderNotFoundException(BindingResult result, Field<?>... fields) {
		super(result, "Could not found order by fields: %s", Arrays.asList(fields));
		this.fields = fields;
	}

	public Field[] getFields() {
		return fields;
	}

}
