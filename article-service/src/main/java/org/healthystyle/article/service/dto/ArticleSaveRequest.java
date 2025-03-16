package org.healthystyle.article.service.dto;

import java.util.List;

import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class ArticleSaveRequest {
	@NotBlank(message = "Укажите название статьи")
	private String title;
	@Valid
	private ImageSaveRequest image;
	@Valid
	@NotEmpty(message ="Укажите хотя бы один фрагмент")
	private List<FragmentSaveRequest> fragments;
	@Valid
	private List<ArticleSourceSaveRequest> sources;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public List<FragmentSaveRequest> getFragments() {
		return fragments;
	}

	public void setFragments(List<FragmentSaveRequest> fragments) {
		this.fragments = fragments;
	}

	public List<ArticleSourceSaveRequest> getSources() {
		return sources;
	}

	public void setSources(List<ArticleSourceSaveRequest> sources) {
		this.sources = sources;
	}

}
