package org.healthystyle.article.repository;

import java.time.Instant;

import org.healthystyle.article.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	@Query("SELECT a FROM Article a ORDER BY a.createdOn DESC")
	Page<Article> find(Pageable pageable);

	@Query("SELECT a FROM Article a WHERE a.title LIKE ':title%'")
	Page<Article> findByTitle(String title, Pageable pageable);

	@Query("SELECT a FROM Article a WHERE a.category.id = :categoryId")
	Page<Article> findByCategory(Long categoryId, Pageable pageable);
	
	@Query("SELECT a FROM Article a WHERE a.author = :author ORDER BY a.createdOn DESC")
	Page<Article> findByAuthor(Long author, Pageable pageable);

	@Query("SELECT a.* FROM Article a LEFT JOIN a.views v WHERE (:start IS NULL OR a.createdOn >= :start) AND (:end IS NULL OR a.createdOn <= :end) AND (:categoryId IS NULL OR a.category.id = :categoryId) AND (:author IS NULL OR a.user_id = :author) GROUP BY a.id ORDER BY COUNT(v.id)")
	Page<Article> findMostWatched(Long categoryId, Long authorId, Instant start, Instant end, Pageable pageable);
	
	@Query("SELECT a.* FROM Article a LEFT JOIN a.marks ma LEFT JOIN ma.mark m WHERE (:start IS NULL OR a.createdOn >= :start) AND (:end IS NULL OR a.createdOn <= :end) AND (:author IS NULL OR a.user_id = :author) GROUP BY a.id ORDER BY AVG(m.value) NULLS LAST")
	Page<Article> findMostMarked(Long categoryId, Long authorId, Instant start, Instant end, Pageable pageable);
	
}
