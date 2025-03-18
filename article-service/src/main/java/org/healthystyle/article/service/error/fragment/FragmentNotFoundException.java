package org.healthystyle.article.service.error.fragment;

import java.util.Arrays;

import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.Field;
import org.springframework.validation.BindingResult;

public class FragmentNotFoundException extends AbstractException {
	private Field<?>[] fields;

	public FragmentNotFoundException(BindingResult result, Field<?>... fields) {
		super(result, "Could not found fragment by fields: %s", Arrays.asList(fields));
		this.fields = fields;
	}

	public Field<?>[] getFields() {
		return fields;
	}
}
