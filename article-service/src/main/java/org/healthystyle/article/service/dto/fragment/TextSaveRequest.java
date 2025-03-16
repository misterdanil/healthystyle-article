package org.healthystyle.article.service.dto.fragment;

import java.util.List;

import org.healthystyle.article.service.dto.fragment.text.TextPartSaveRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class TextSaveRequest extends OrderSaveRequest {
	@NotEmpty(message ="Укажите части текста")
	@Valid
	private List<TextPartSaveRequest> textParts;

	public List<TextPartSaveRequest> getTextParts() {
		return textParts;
	}

	public void setTextParts(List<TextPartSaveRequest> textParts) {
		this.textParts = textParts;
	}

}
