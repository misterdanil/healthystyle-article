package org.healthystyle.article.web.dto.fragment;

public class QuoteDto extends OrderDto {
	private String name;
	private String text;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getType() {
		return "quote";
	}

}
