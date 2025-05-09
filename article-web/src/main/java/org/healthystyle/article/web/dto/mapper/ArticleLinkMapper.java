package org.healthystyle.article.web.dto.mapper;

import org.healthystyle.article.model.fragment.ArticleLink;
import org.healthystyle.article.web.dto.fragment.ArticleLinkDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArticleLinkMapper {
	@Mapping(source = "link.id", target = "articleId")
	ArticleLinkDto toDto(ArticleLink articleLink);
}
