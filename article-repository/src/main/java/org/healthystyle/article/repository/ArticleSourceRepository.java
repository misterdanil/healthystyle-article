package org.healthystyle.article.repository;

import org.healthystyle.article.model.ArticleSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSourceRepository extends JpaRepository<ArticleSource, Long> {
	@Query("SELECT a FROM ArticleSource a WHERE a.article.id = :articleId AND a.order = :order")
	ArticleSource findByArticleAndOrder(Long articleId, Integer order);

	@Query("SELECT s FROM ArticleSource s INNER JOIN s.article a WHERE a.id = :articleId ORDER BY s.order")
	Page<ArticleSource> findByArticle(Long articleId, Pageable pageable);

	@Query("SELECT EXISTS (SELECT ArticleSource a WHERE a.article.id = :articleId AND a.order = :order)")
	boolean existsByArticleAndOrder(Long articleId, Integer order);
}
