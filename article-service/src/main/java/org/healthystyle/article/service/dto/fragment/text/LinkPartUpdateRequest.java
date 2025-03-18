package org.healthystyle.article.service.dto.fragment.text;

import org.healthystyle.article.model.fragment.text.part.LinkPart;
import org.healthystyle.article.model.fragment.text.part.TextPart;

import jakarta.validation.constraints.NotBlank;

public class LinkPartUpdateRequest extends TextPartUpdateRequest {
	@NotBlank(message = "Укажите ссылку на ресурс")
	private String source;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public boolean support(TextPart textPart) {
		return textPart.getClass() == LinkPart.class;
	}
	
	
}
