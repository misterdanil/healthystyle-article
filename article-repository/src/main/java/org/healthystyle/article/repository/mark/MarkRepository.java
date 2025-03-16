package org.healthystyle.article.repository.mark;

import org.healthystyle.article.model.mark.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
	@Query("SELECT m FROM Mark m WHERE m.value = :value")
	Mark findByValue(Integer value);
}
