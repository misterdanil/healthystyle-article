package org.healthystyle.article.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ImageSaveRequest {
	@NotNull(message = "Укажите идентификатор изображения")
	private Long imageId;
	@Valid
	private SourceSaveRequest source;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public SourceSaveRequest getSource() {
		return source;
	}

	public void setSource(SourceSaveRequest source) {
		this.source = source;
	}

}
