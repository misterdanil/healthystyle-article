package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.text.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {

}
