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

	@Query("SELECT a FROM Article a WHERE a.user_id = :author ORDER by a.createdOn DESC")
	Page<Article> findByAuthor(Long author, Pageable pageable);

	@Query("SELECT a FROM Article a WHERE a.category.id = :categoryId")
	Page<Article> findByCategory(Long categoryId, Pageable pageable);

	@Query("SELECT a FROM Article a LEFT JOIN a.views v WHERE (:start IS NULL OR a.createdOn >= :start) AND (:end IS NULL OR a.createdOn <= :end) GROUP BY a.id ORDER BY COUNT(v.id)")
	Page<Article> findMostWatched(Instant start, Instant end, Pageable pageable);
	
	
}
