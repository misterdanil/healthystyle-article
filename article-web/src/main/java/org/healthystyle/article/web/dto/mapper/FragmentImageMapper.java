package org.healthystyle.article.web.dto.mapper;

import org.healthystyle.article.model.fragment.FragmentImage;
import org.healthystyle.article.web.dto.fragment.FragmentImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FragmentImageMapper {
	FragmentImageDto toDto(FragmentImage image);
}
