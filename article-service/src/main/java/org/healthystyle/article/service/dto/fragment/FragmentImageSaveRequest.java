package org.healthystyle.article.service.dto.fragment;

import org.healthystyle.article.service.dto.ImageSaveRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class FragmentImageSaveRequest extends OrderSaveRequest {
	@NotNull(message = "Укажите фотографию")
	@Valid
	private ImageSaveRequest image;

	public ImageSaveRequest getImage() {
		return image;
	}

	public void setImage(ImageSaveRequest image) {
		this.image = image;
	}

}
