package org.healthystyle.article.service.error.fragment.text;

import org.healthystyle.util.error.Field;

public class TextIdField extends Field<Long> {

	public TextIdField(Long value) {
		super(value);
	}

	@Override
	public String getName() {
		return "text id";
	}

}
