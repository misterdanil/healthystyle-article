package org.healthystyle.article.service.fragment.part;

import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.model.fragment.text.part.LinkPart;
import org.healthystyle.article.service.dto.fragment.text.LinkPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.LinkPartUpdateRequest;
import org.healthystyle.article.service.error.fragment.text.part.LinkPartNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface LinkPartService {
	LinkPart findById(Long id) throws ValidationException, LinkPartNotFoundException;

	LinkPart save(LinkPartSaveRequest saveRequest, Text text);

	void update(LinkPartUpdateRequest updateRequest, Long id) throws ValidationException, LinkPartNotFoundException;
}
