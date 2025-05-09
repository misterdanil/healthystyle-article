package org.healthystyle.article.web.dto.mapper;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.ViewService;
import org.healthystyle.article.service.mark.MarkArticleService;
import org.healthystyle.article.web.dto.ArticleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CategoryMapper.class)
public abstract class ArticleMapper {
	@Autowired
	private MarkArticleService markArticleService;
	@Autowired
	private ViewService viewService;

	@Mapping(source = "id", target = "averageMark", qualifiedByName = "mapAvgMarkFromId")
	@Mapping(source = "id", target = "countViews", qualifiedByName = "mapCountViewsFromId")
	public abstract ArticleDto toDto(Article article);

	@Named("mapAvgMarkFromId")
	Float mapAvgMarkFromId(Long id) {
		return markArticleService.getAvgByArticle(id);
	}

	@Named("mapCountViewsFromId")
	Integer mapCountViewsFromId(Long id) {
		return viewService.countByArticle(id);
	}
}
