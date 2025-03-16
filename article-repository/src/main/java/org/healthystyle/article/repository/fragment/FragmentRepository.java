package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.Fragment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentRepository extends JpaRepository<Fragment, Long> {
	@Query("SELECT f FROM Fragment f WHERE f.article.id = :articleId ORDER BY f.order")
	Page<Fragment> findByArticle(Long articleId, Pageable pageable);
}
