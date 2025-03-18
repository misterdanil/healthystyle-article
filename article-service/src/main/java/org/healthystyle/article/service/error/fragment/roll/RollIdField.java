package org.healthystyle.article.service.error.fragment.roll;

import org.healthystyle.util.error.Field;

public class RollIdField extends Field<Long> {

	public RollIdField(Long value) {
		super(value);
	}

	@Override
	public String getName() {
		return "roll id";
	}

}
