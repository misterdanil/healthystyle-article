package org.healthystyle.article.service.dto.fragment.text;

import jakarta.validation.constraints.NotBlank;

public class LinkPartSaveRequest extends TextPartSaveRequest {
	@NotBlank(message = "Укажите ссылку на ресурс")
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
