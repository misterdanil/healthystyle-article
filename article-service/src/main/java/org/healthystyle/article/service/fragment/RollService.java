package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Roll;
import org.healthystyle.article.service.dto.fragment.RollSaveRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface RollService {
	Roll findById(Long id) throws ValidationException, RollNotFoundException;

	Roll save(RollSaveRequest saveRequest, Fragment fragment)
			throws ValidationException, RollNotFoundException, OrderExistException, PreviousOrderNotFoundException;
}
