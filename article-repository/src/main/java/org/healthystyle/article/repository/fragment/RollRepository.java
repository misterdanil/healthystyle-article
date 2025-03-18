package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.Roll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollRepository extends JpaRepository<Roll, Long> {

}
