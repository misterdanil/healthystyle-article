package org.healthystyle.article.service.dto.fragment.text;

import org.healthystyle.article.model.fragment.text.part.CursivePart;
import org.healthystyle.article.model.fragment.text.part.TextPart;

public class CursivePartUpdateRequest extends TextPartUpdateRequest {

	@Override
	public boolean support(TextPart textPart) {
		return textPart.getClass() == CursivePart.class;
	}

}
