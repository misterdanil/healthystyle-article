package org.healthystyle.article.web.dto.mapper;

import org.healthystyle.article.model.fragment.Quote;
import org.healthystyle.article.web.dto.fragment.QuoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuoteMapper {
	QuoteDto toDto(Quote quote);
}
