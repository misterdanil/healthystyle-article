package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.Quote;
import org.healthystyle.article.service.dto.fragment.QuoteSaveRequest;
import org.healthystyle.article.service.dto.fragment.QuoteUpdateRequest;
import org.healthystyle.article.service.error.fragment.quote.QuoteNotFoundException;
import org.healthystyle.util.error.ValidationException;

public interface QuoteService {
	Quote findById(Long id) throws ValidationException, QuoteNotFoundException;

	Quote save(QuoteSaveRequest saveRequest, Fragment fragment);

	void update(QuoteUpdateRequest updateRequest, Long quoteId) throws ValidationException, QuoteNotFoundException;
}
