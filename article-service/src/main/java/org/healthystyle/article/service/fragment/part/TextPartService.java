package org.healthystyle.article.service.fragment.part;

import org.healthystyle.article.model.fragment.text.part.TextPart;
import org.healthystyle.article.service.dto.fragment.text.TextPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.TextPartUpdateRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.service.error.fragment.text.part.LinkPartNotFoundException;
import org.healthystyle.article.service.error.fragment.text.part.TextPartNotFoundException;
import org.healthystyle.article.service.util.MethodNameHelper;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;

public interface TextPartService {
	static final String[] FIND_BY_TEXT_AND_ORDER_PARAM_NAMES = MethodNameHelper
			.getMethodParamNames(TextPartService.class, "findByTextAndOrder", Long.class, Integer.class);

	static final String[] FIND_BY_TEXT_PARAM_NAMES = MethodNameHelper.getMethodParamNames(TextPartService.class,
			"findByText", Long.class, int.class, int.class);

	TextPart findById(Long id) throws ValidationException, TextPartNotFoundException;

	TextPart findByTextAndOrder(Long textId, Integer order) throws ValidationException, TextPartNotFoundException;

	Page<TextPart> findByText(Long textId, int page, int limit) throws ValidationException;

	TextPart save(TextPartSaveRequest saveRequest, Long textId)
			throws ValidationException, OrderExistException, PreviousOrderNotFoundException, TextNotFoundException;

	void update(TextPartUpdateRequest updateRequest, Long textPartId) throws ValidationException,
			TextPartNotFoundException, PreviousOrderNotFoundException, LinkPartNotFoundException;
}
