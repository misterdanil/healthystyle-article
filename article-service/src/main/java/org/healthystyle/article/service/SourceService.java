package org.healthystyle.article.service;

import org.healthystyle.article.model.Source;
import org.healthystyle.article.service.dto.SourceSaveRequest;
import org.healthystyle.article.service.dto.SourceUpdateRequest;
import org.healthystyle.article.service.error.SourceNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface SourceService {
	Source findById(Long id) throws ValidationException, SourceNotFoundException;

	Source save(SourceSaveRequest saveRequest) throws ValidationException;

	void update(SourceUpdateRequest updateRequest, Long id) throws ValidationException, SourceNotFoundException;
}
