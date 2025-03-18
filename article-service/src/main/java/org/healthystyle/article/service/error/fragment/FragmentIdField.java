package org.healthystyle.article.service.error.fragment;

import org.healthystyle.util.error.Field;

public class FragmentIdField extends Field<Long> {

	public FragmentIdField(Long value) {
		super(value);
	}

	@Override
	public String getName() {
		return "fragment id";
	}

}
