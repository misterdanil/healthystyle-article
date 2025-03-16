package org.healthystyle.article.service.dto.fragment;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class RollSaveRequest extends OrderSaveRequest {
	@NotEmpty(message = "Список не может быть пустым")
	@Valid
	private List<RollElementSaveRequest> rollElements;

	public List<RollElementSaveRequest> getRollElements() {
		return rollElements;
	}

	public void setRollElements(List<RollElementSaveRequest> rollElements) {
		this.rollElements = rollElements;
	}

}
