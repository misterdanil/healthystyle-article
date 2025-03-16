package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.RollElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RollElementRepository extends JpaRepository<RollElement, Long> {
	@Query("SELECT re FROM RollElement re WHERE re.roll.id = :rollId ORDER BY re.order DESC")
	Page<RollElement> findByRoll(Long rollId, Pageable pageable);
}
