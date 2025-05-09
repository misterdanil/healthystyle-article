package org.healthystyle.article.web.dto.fragment.text;

import java.util.List;

import org.healthystyle.article.web.dto.fragment.OrderDto;

public class TextDto extends OrderDto {
	private List<TextPartDto> textParts;

	public List<TextPartDto> getTextParts() {
		return textParts;
	}

	public void setTextParts(List<TextPartDto> textParts) {
		this.textParts = textParts;
	}

	@Override
	public String getType() {
		return "text";
	}
	
	

}
