package org.healthystyle.article.service.dto.fragment;

import java.util.List;

import org.healthystyle.article.service.dto.fragment.text.TextPartSaveRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class TextSaveRequest extends OrderSaveRequest {
	@NotEmpty(message ="Укажите части текста")
	private List<TextPartSaveRequest> parts;

	public List<TextPartSaveRequest> getParts() {
		return parts;
	}

	public void setParts(List<TextPartSaveRequest> textParts) {
		this.parts = textParts;
	}

}
