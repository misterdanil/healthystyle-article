package org.healthystyle.article.web.dto.fragment;

import org.healthystyle.article.web.dto.ImageDto;

public class FragmentImageDto extends OrderDto {
	private ImageDto image;

	public ImageDto getImage() {
		return image;
	}

	public void setImage(ImageDto image) {
		this.image = image;
	}
	
	@Override
	public String getType() {
		return "image";
	}

}
