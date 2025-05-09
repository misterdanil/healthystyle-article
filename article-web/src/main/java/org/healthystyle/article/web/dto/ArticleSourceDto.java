package org.healthystyle.article.web.dto;

public class ArticleSourceDto {
	private Long id;
	private Integer order;
	private SourceDto source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public SourceDto getSource() {
		return source;
	}

	public void setSource(SourceDto source) {
		this.source = source;
	}

}
