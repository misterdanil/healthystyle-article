package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.fragment.id = :fragmentId AND o.order = :order")
	Order findByFragmentAndOrder(Long fragmentId, Integer order);

	@Query("SELECT o FROM Fragment f INNER JOIN f.orders o LEFT JOIN Text t ON o.id = t.id LEFT JOIN ArticleLink al ON al.id = o.id LEFT JOIN FragmentImage fi ON fi.id = o.id LEFT JOIN Quote q ON q.id = o.id LEFT JOIN Roll r ON r.id = o.id WHERE f.id =:fragmentId ORDER BY o.order")
	Page<Order> findByFragment(Long fragmentId, Pageable pageable);

	@Query("SELECT EXISTS (SELECT o FROM Order o WHERE o.fragment.id = :fragmentId AND o.order = :order)")
	boolean existsByFragmentAndOrder(Long fragmentId, Integer order);
}
