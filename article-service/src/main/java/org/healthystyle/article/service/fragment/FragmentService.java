package org.healthystyle.article.service.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FragmentService {
	Page<Fragment> findByArticle(Long articleId, Pageable pageable);
	
	Fragment save(FragmentSaveRequest saveRequest, Long articleId);
	
	void update(FragmentUpdateRequest updateRequest, Long fragmentId);
}
