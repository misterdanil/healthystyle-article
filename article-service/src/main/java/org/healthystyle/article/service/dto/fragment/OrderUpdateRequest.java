package org.healthystyle.article.service.dto.fragment;

import org.healthystyle.article.service.dto.fragment.text.BoldPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.LinkPartSaveRequest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = ArticleLinkUpdateRequest.class, name = "articleLink"),
		@JsonSubTypes.Type(value = BoldPartSaveRequest.class, name = "bold"),
		@JsonSubTypes.Type(value = LinkPartSaveRequest.class, name = "link") })
public abstract class OrderUpdateRequest {
	private Integer order;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
