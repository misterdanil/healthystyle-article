package org.healthystyle.article.service.error;

import org.healthystyle.util.error.Field;

public class OrderField extends Field<Integer> {

	public OrderField(Integer value) {
		super(value);
	}

	@Override
	public String getName() {
		return "order";
	}

}
