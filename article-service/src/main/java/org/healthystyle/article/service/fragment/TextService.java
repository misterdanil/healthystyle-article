package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.service.dto.fragment.TextSaveRequest;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface TextService {
	Text findById(Long id) throws ValidationException, TextNotFoundException;

	Text findFirstByArticle(Long articleId) throws ValidationException, TextNotFoundException;

	Text save(TextSaveRequest saveRequest, Fragment fragment)
			throws ValidationException, OrderExistException, PreviousOrderNotFoundException, TextNotFoundException;
}
