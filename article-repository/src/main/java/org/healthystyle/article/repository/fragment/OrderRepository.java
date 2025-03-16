package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o.*, al.*, fi.*, q.*, r.*, t.* FROM Fragment f INNER JOIN Order o ON o.fragment = f LEFT JOIN o.text t LEFT JOIN o.articleLink al LEFT JOIN o.fragmentImage fi LEFT JOIN o.quote q LEFT JOIN o.roll r ORDER BY o.order")
	Page<Order> findByFragment(Long fragmentId, Pageable pageable);
}
