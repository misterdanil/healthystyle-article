package org.healthystyle.article.service.dto;

public class ArticleSourceSaveRequest {
	private Integer order;
	private SourceSaveRequest source;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public SourceSaveRequest getSource() {
		return source;
	}

	public void setSource(SourceSaveRequest source) {
		this.source = source;
	}

}
