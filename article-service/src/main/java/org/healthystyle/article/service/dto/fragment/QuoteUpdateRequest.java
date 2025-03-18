package org.healthystyle.article.service.dto.fragment;

import jakarta.validation.constraints.NotBlank;

public class QuoteUpdateRequest extends OrderUpdateRequest {
	@NotBlank(message = "Укажите источника цитаты")
	private String name;
	@NotBlank(message = "Укажите цитату")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
