package org.healthystyle.article.web.dto.mapper.text;

import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.web.dto.fragment.text.TextDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TextPartMapper.class)
public interface TextMapper {
	TextDto toDto(Text text);
}
