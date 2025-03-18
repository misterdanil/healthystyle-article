package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.RollElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RollElementRepository extends JpaRepository<RollElement, Long> {
	@Query("SELECT re FROM RollElement re WHERE re.roll.id = :rollId AND re.order = :order")
	RollElement findByRollAndOrder(Long rollId, Integer order);

	@Query("SELECT re FROM RollElement re WHERE re.roll.id = :rollId ORDER BY re.order DESC")
	Page<RollElement> findByRoll(Long rollId, Pageable pageable);

	@Query("SELECT EXISTS (SELECT re FROM RollElement re WHERE re.roll.id = :rollId AND re.order = :order)")
	boolean existsByRollAndOrder(Long rollId, Integer order);
	
	@Modifying
	@Query("UPDATE RollElement re SET re.order = (:relativeOrder - 1) WHERE re.roll.id = :rollId AND re.order > :relativeOrder")
	void shiftAllNextBack(Integer relativeOrder, Long rollId);
}
