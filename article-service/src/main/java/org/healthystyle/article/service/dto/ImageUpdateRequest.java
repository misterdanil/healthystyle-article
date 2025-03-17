package org.healthystyle.article.service.dto;

import jakarta.validation.constraints.NotNull;

public class ImageUpdateRequest {
	@NotNull(message = "Укажите идентификатор картинки")
	private Long imageId;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

}
