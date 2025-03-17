package org.healthystyle.article.repository;

import org.healthystyle.article.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
