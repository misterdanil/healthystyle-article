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

	@Query("SELECT a FROM Article a WHERE a.title LIKE CONCAT('%', LOWER(:title), '%')")
	Page<Article> findByTitle(String title, Pageable pageable);

	@Query("SELECT a FROM Article a WHERE a.category.id = :categoryId")
	Page<Article> findByCategory(Long categoryId, Pageable pageable);

	@Query("SELECT a FROM Article a WHERE a.author = :author ORDER BY a.createdOn DESC")
	Page<Article> findByAuthor(Long author, Pageable pageable);

	@Query("SELECT a FROM Article a LEFT JOIN a.views v WHERE (CAST(:start AS TIMESTAMP) IS NULL OR a.createdOn >= :start) AND (CAST(:end AS TIMESTAMP) IS NULL OR a.createdOn <= :end) AND (:categoryId IS NULL OR a.category.id = :categoryId) AND (:authorId IS NULL OR a.author = :authorId) AND LOWER(a.title) LIKE CONCAT(LOWER(:title), '%') GROUP BY a.id ORDER BY COUNT(v.id)")
	Page<Article> findMostWatched(String title, Long categoryId, Long authorId, Instant start, Instant end,
			Pageable pageable);

	@Query("SELECT a FROM Article a LEFT JOIN a.marks ma LEFT JOIN ma.mark m WHERE (CAST(:start AS TIMESTAMP) IS NULL OR a.createdOn >= :start) AND (CAST(:end AS TIMESTAMP) IS NULL OR a.createdOn <= :end) AND (:categoryId IS NULL OR a.category.id = :categoryId) AND (:authorId IS NULL OR a.author = :authorId) AND LOWER(a.title) LIKE CONCAT(LOWER(:title), '%') GROUP BY a.id ORDER BY AVG(m.value) NULLS LAST")
	Page<Article> findMostMarked(String title, Long categoryId, Long authorId, Instant start, Instant end,
			Pageable pageable);
}
