package org.healthystyle.article.web.dto.fragment;

import java.util.List;

public class RollDto extends OrderDto {
	private List<RollElementDto> rollElements;

	public List<RollElementDto> getRollElements() {
		return rollElements;
	}

	public void setRollElements(List<RollElementDto> rollElements) {
		this.rollElements = rollElements;
	}

	@Override
	public String getType() {
		return "roll";
	}

}
