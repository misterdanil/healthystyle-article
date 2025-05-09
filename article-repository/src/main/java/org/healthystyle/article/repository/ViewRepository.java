package org.healthystyle.article.repository;

import org.healthystyle.article.model.View;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
	@Query("SELECT COUNT(v) FROM View v WHERE v.article.id = :articleId GROUP BY v.article.id")
	Integer countByArticle(Long articleId);

	@Query("SELECT v FROM View v WHERE v.userId = :userId ORDER BY v.createdOn DESC")
	Page<View> findByUserId(Long userId, Pageable pageable);

	@Query("SELECT v FROM View v WHERE v.article.id = :articleId ORDER BY v.createdOn DESC")
	Page<View> findByArticle(Long articleId, Pageable pageable);

	@Query("SELECT EXISTS (SELECT v FROM View v WHERE v.userId = :userId AND v.article.id = :articleId)")
	boolean existsByUserIdAndArticle(Long userId, Long articleId);

	@Query("SELECT EXISTS (SELECT v FROM View v WHERE v.ip = :ip AND v.article.id = :articleId)")
	boolean existsByIpAndArticle(String ip, Long articleId);
}
