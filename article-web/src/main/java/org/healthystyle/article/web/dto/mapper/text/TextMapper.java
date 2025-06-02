package org.healthystyle.article.web.dto.mapper.text;

import java.util.List;

import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.model.fragment.text.part.TextPart;
import org.healthystyle.article.web.dto.fragment.text.TextDto;
import org.healthystyle.article.web.dto.fragment.text.TextPartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TextMapper {
	@Autowired
	private TextPartMapper textPartMapper;

	@Mapping(source = "textParts", target = "textParts", qualifiedByName = "sortParts")
	public abstract TextDto toDto(Text text);

	@Named("sortParts")
	public List<TextPartDto> sortParts(List<TextPart> parts) {
		List<TextPartDto> dtos = textPartMapper.toDtos(parts);

		dtos.sort((a, b) -> a.getOrder() - b.getOrder());

		return dtos;

	}
}
