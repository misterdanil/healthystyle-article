package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.RollElement;
import org.healthystyle.article.service.dto.fragment.RollElementSaveRequest;
import org.healthystyle.article.service.dto.fragment.RollElementUpdateRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollElementNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.util.MethodNameHelper;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;

public interface RollElementService {
	static final String[] FIND_BY_ROLL_AND_ORDER_PARAM_NAMES = MethodNameHelper
			.getMethodParamNames(RollElementService.class, "findByRollAndOrder", Long.class, Integer.class);

	static final String[] FIND_BY_ROLL_PARAM_NAMES = MethodNameHelper.getMethodParamNames(RollElementService.class,
			"findByRoll", Long.class, int.class, int.class);

	RollElement findById(Long id) throws ValidationException, RollElementNotFoundException;

	RollElement findByRollAndOrder(Long rollId, Integer order) throws ValidationException, RollElementNotFoundException;

	Page<RollElement> findByRoll(Long rollId, int page, int limit) throws ValidationException;

	RollElement save(RollElementSaveRequest saveRequest, Long rollId)
			throws ValidationException, RollNotFoundException, OrderExistException, PreviousOrderNotFoundException;

	void update(RollElementUpdateRequest updateRequest, Long rollElementId)
			throws ValidationException, PreviousOrderNotFoundException, RollElementNotFoundException;

	void deleteById(Long id) throws ValidationException, RollElementNotFoundException;
}
