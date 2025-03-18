package org.healthystyle.article.service.dto.fragment.text;

import org.healthystyle.article.model.fragment.text.part.BoldPart;
import org.healthystyle.article.model.fragment.text.part.TextPart;

public class BoldPartUpdateRequest extends TextPartUpdateRequest {

	@Override
	public boolean support(TextPart textPart) {
		return textPart.getClass() == BoldPart.class;
	}

}
