package org.healthystyle.article.service;

import java.util.List;

import org.healthystyle.article.model.Category;
import org.healthystyle.article.service.dto.CategorySaveRequest;
import org.healthystyle.article.service.dto.CategoryUpdateRequest;
import org.healthystyle.article.service.error.CategoryExistException;
import org.healthystyle.article.service.error.CategoryNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.util.MethodNameHelper;
import org.healthystyle.util.error.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface CategoryService {
	static final String[] FIND_BY_TITLE_PARAM_NAMES = MethodNameHelper.getMethodParamNames(CategoryService.class,
			"findByTitle", String.class, int.class, int.class);

	Category findById(Long id) throws ValidationException, CategoryNotFoundException;

	Page<Category> findByTitle(String title, int page, int limit) throws ValidationException;

	List<Category> findAllRootCategories();

	List<Category> findAllChildCategories(Long categoryId);

	Category save(CategorySaveRequest saveRequest) throws ValidationException, CategoryExistException,
			CategoryNotFoundException, OrderExistException, PreviousOrderNotFoundException;

	void update(CategoryUpdateRequest updateRequest, Long categoryId);
}
