package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;

public interface FragmentService {
	Fragment save(FragmentSaveRequest saveRequest, Long articleId);
}
