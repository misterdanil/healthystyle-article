package org.healthystyle.article.repository;

import java.util.List;

import org.healthystyle.article.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("SELECT c FROM Category c WHERE c.title LIKE '%:title' ORDER BY c.order")
	Page<Category> findByTitle(String title, Pageable pageable);
	
	@Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL ORDER by c.order")
	List<Category> findAllRootCategories();
	
	@Query("SELECT c FROM Category c WHERE c.parentCategory.id = :categoryId ORDER BY c.order")
	List<Category> findAllChildCategories(Long categoryId);
}
