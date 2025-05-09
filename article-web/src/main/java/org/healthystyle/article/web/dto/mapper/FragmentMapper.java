package org.healthystyle.article.web.dto.mapper;

import java.util.List;

import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.web.dto.fragment.FragmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FragmentMapper {
	FragmentDto toDto(Fragment fragment);

	List<FragmentDto> toDtos(List<Fragment> fragments);
}
