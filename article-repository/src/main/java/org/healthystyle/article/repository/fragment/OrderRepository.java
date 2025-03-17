package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o.*, al.*, fi.*, q.*, r.*, t.* FROM Fragment f INNER JOIN f.orders LEFT JOIN Text t ON o.id = t.id LEFT JOIN ArticleLink al ON al.id = o.id LEFT JOIN FragmentImage fi ON fi.id = o.id LEFT JOIN Quote q ON q.id = o.id LEFT JOIN Roll r ON r.id = o.id ORDER BY o.order")
	Page<Order> findByFragment(Long fragmentId, Pageable pageable);
}
