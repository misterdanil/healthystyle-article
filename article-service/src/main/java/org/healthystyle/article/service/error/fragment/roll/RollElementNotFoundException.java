package org.healthystyle.article.service.error.fragment.roll;

import java.util.Arrays;

import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.Field;
import org.springframework.validation.BindingResult;

public class RollElementNotFoundException extends AbstractException {
	private Field<?>[] fields;

	public RollElementNotFoundException(BindingResult result, Field<?>... fields) {
		super(result, "Could not found roll by fields: {}", Arrays.asList(fields));
		this.fields = fields;
	}

	public Field<?>[] getFields() {
		return fields;
	}

}
