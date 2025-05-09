package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.text.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {
	@Query("SELECT t FROM Text t INNER JOIN t.fragment f INNER JOIN t.textParts tp WHERE f.article.id = :articleId ORDER BY t.order LIMIT 1")
	Text findFirstByArticle(Long articleId);
}
