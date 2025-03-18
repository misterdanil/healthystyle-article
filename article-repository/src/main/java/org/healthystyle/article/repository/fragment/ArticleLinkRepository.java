package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.ArticleLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLinkRepository extends JpaRepository<ArticleLink, Long> {
	@Query("SELECT EXISTS (SELECT al FROM ArticleLink al WHERE al.fragment.id = :fragmentId AND al.link.id = :articleId)")
	boolean existsByFragmentAndLink(Long fragmentId, Long articleId);
}
