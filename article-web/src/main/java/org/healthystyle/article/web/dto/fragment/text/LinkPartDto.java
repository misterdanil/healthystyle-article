package org.healthystyle.article.web.dto.fragment.text;

public class LinkPartDto extends TextPartDto {
	private String source;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String getType() {
		return "link";
	}

}
