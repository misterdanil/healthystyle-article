package org.healthystyle.article.web.dto.mapper.text;

import java.util.List;

import org.healthystyle.article.model.fragment.text.part.BoldPart;
import org.healthystyle.article.model.fragment.text.part.CursivePart;
import org.healthystyle.article.model.fragment.text.part.LinkPart;
import org.healthystyle.article.model.fragment.text.part.TextPart;
import org.healthystyle.article.web.dto.fragment.text.BoldPartDto;
import org.healthystyle.article.web.dto.fragment.text.CursivePartDto;
import org.healthystyle.article.web.dto.fragment.text.LinkPartDto;
import org.healthystyle.article.web.dto.fragment.text.TextPartDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = LinkPartMapper.class)
public interface TextPartMapper {
	@SubclassMappings(value = { @SubclassMapping(source = CursivePart.class, target = CursivePartDto.class),
			@SubclassMapping(source = BoldPart.class, target = BoldPartDto.class),
			@SubclassMapping(source = LinkPart.class, target = LinkPartDto.class) })
	TextPartDto toDto(TextPart textPart);
	
	List<TextPartDto> toDtos(List<TextPart> textParts);
}
