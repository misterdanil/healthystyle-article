package org.healthystyle.article.repository.fragment.text;

import org.healthystyle.article.model.fragment.text.part.TextPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TextPartRepository extends JpaRepository<TextPart, Long> {
	@Query("SELECT * FROM TextPart tp LEFT JOIN BoldPart bp ON bp.id = tp.id LEFT JOIN CursivePart cp ON cp.id = tp.id LEFT JOIN LinkPart lp ON lp.id = tp.id WHERE tp.text.id = :textId ORDER BY tp.order")
	Page<TextPart> findByText(Long textId, Pageable pageable);
}
