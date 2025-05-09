package org.healthystyle.article.service.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentSaveRequest {
	@NotBlank(message = "Укажите текст")
	private String text;
	private Long replyTo;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Long replyTo) {
		this.replyTo = replyTo;
	}

}
