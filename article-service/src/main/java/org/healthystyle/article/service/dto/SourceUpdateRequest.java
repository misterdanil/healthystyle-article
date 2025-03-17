package org.healthystyle.article.service.dto;

import jakarta.validation.constraints.NotBlank;

public class SourceUpdateRequest {
	@NotBlank(message = "Укажите описание источника")
	private String description;
	@NotBlank(message = "Укажите источник")
	private String link;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
