package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>{

}
