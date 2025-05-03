package org.healthystyle.article.service.dto.fragment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = ArticleLinkSaveRequest.class, name = "link"),
		@JsonSubTypes.Type(value = FragmentImageSaveRequest.class, name = "image"),
		@JsonSubTypes.Type(value = QuoteSaveRequest.class, name = "quote"),
		@JsonSubTypes.Type(value = RollSaveRequest.class, name = "roll") })
public abstract class OrderSaveRequest {
	@NotNull(message = "Укажите порядок части фрагмента")
	@Positive(message = "Порядок должен быть положительным и больше нуля")
	private Integer order;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
