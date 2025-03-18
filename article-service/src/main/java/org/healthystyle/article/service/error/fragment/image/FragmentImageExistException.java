package org.healthystyle.article.service.error.fragment.image;

import java.util.Arrays;

import org.healthystyle.util.error.AbstractException;
import org.healthystyle.util.error.Field;
import org.springframework.validation.BindingResult;

public class FragmentImageExistException extends AbstractException {
	private Field<?>[] fields;

	public FragmentImageExistException(BindingResult result, Field<?>... fields) {
		super(result, "A fragment image has already existed by fields: {}", Arrays.asList(fields));
		this.fields = fields;
	}

	public Field<?>[] getFields() {
		return fields;
	}

}
