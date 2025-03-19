package org.healthystyle.article.service.dto.mark;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MarkSaveRequest {
	@NotNull(message = "Укажите оценку")
	@Min(value = 1)
	@Max(value = 5)
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
	
}
