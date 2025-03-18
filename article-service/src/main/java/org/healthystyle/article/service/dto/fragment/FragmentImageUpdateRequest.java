package org.healthystyle.article.service.dto.fragment;

import org.healthystyle.article.service.dto.ImageUpdateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class FragmentImageUpdateRequest extends OrderUpdateRequest {
	@NotNull(message = "Укажите изображение для замены")
	private ImageUpdateRequest image;

	public ImageUpdateRequest getImage() {
		return image;
	}

	public void setImage(ImageUpdateRequest image) {
		this.image = image;
	}

}
