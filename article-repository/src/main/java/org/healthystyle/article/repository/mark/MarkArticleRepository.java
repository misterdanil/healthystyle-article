package org.healthystyle.article.repository.mark;

import org.healthystyle.article.model.mark.MarkArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkArticleRepository extends JpaRepository<MarkArticle, Long> {
	@Query("SELECT AVG(m.value) FROM MarkArticle ma INNER JOIN ma.mark m WHERE ma.article.id = :articleId GROUP BY ma.article.id")
	Float getAvgByArticle(Long articleId);

	@Query("SELECT ma FROM MarkArticle ma  WHERE ma.article.id = :articleId ORDER BY ma.createdOn DESC")
	Page<MarkArticle> findByArticle(Long articleId, Pageable pageable);

	@Query("SELECT ma FROM MarkArticle ma INNER JOIN ma.mark m WHERE ma.article.id = :articleId AND m.value = :value ORDER BY ma.createdOn DESC")
	Page<MarkArticle> findByArticleAndValue(Long articleId, Pageable pageable);

	@Query("SELECT ma FROM MarkArticle ma WHERE ma.userId = :userId ORDER BY ma.createdOn DESC")
	Page<MarkArticle> findByUserId(Long userId, Pageable pageable);
	
	@Query("SELECT ma FROM MarkArticle ma WHERE ma.userId = :userId AND ma.article.id = :articleId ORDER BY ma.createdOn DESC")
	MarkArticle findByUserIdAndArticle(Long userId, Long articleId);

	@Query("SELECT ma FROM MarkArticle ma INNER JOIN ma.mark m WHERE ma.userId = :userId AND m.value = :value ORDER BY ma.createdOn DESC")
	Page<MarkArticle> findByUserIdAndValue(Long userId, Integer value, Pageable pageable);
}
