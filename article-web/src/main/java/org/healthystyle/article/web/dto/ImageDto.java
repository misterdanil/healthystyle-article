package org.healthystyle.article.web.dto;

public class ImageDto {
	private Long id;
	private Long imageId;
	private SourceDto source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public SourceDto getSource() {
		return source;
	}

	public void setSource(SourceDto source) {
		this.source = source;
	}

}
