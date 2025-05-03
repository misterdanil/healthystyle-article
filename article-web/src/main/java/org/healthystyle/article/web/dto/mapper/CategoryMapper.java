package org.healthystyle.article.web.dto.mapper;

import org.healthystyle.article.model.Category;
import org.healthystyle.article.web.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
	CategoryDto toDto(Category category);
}
