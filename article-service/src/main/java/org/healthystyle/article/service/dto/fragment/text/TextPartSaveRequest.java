package org.healthystyle.article.service.dto.fragment.text;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = CursivePartSaveRequest.class, name = "cursive"),
		@JsonSubTypes.Type(value = BoldPartSaveRequest.class, name = "bold"),
		@JsonSubTypes.Type(value = LinkPartSaveRequest.class, name = "link") })
public class TextPartSaveRequest {
	@NotBlank(message = "Укажите текст")
	private String text;
	@NotNull(message = "Укажите порядок части текста")
	@Positive(message = "Порядок должен быть положительным и больше нуля")
	private Integer order;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
