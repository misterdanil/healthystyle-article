package org.healthystyle.article.service.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ImageSaveRequest {
	@NotNull(message = "Укажите изображение")
	private MultipartFile file;
	@Valid
	private SourceSaveRequest source;
	private String root;
	private String relativePath;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public SourceSaveRequest getSource() {
		return source;
	}

	public void setSource(SourceSaveRequest source) {
		this.source = source;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
}
