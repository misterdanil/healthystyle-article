package org.healthystyle.article.repository;

import java.util.List;

import org.healthystyle.article.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	@Query("SELECT c FROM Comment c WHERE c.userId = :userId ORDER BY c.createdOn DESC")
	Page<Comment> findByUserId(Long userId, Pageable pageable);
	
	@Query("SELECT c FROM Comment c WHERE c.article.id = :articleId AND c.replyTo IS NULL ORDER BY c.createdOn DESC")
	Page<Comment> findRootsByArticle(Long articleId, Pageable pageable);
	
	@Query(value = "WITH RECURSIVE REPLIES AS ("
			+ "SELECT c.* FROM comment c WHERE c.id = :commentId"
			+ "UNION ALL"
			+ "SELECT c.* FROM REPLIES r INNER JOIN comment c ON c.replyTo = r.id"
			+ ")", nativeQuery = true)
	List<Comment> findRepliesByComment(Long commentId, int page, int limit);
}
