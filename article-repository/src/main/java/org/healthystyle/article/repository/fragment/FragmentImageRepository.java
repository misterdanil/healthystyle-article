package org.healthystyle.article.repository.fragment;

import org.healthystyle.article.model.fragment.FragmentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentImageRepository extends JpaRepository<FragmentImage, Long> {

}
