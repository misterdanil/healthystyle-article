package org.healthystyle.article.web.dto.mapper.text;

import org.healthystyle.article.model.fragment.text.part.LinkPart;
import org.healthystyle.article.web.dto.fragment.text.LinkPartDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LinkPartMapper {
	LinkPartDto toDto(LinkPart linkPart);
}
