package org.healthystyle.article.web.dto.mapper;

import org.healthystyle.article.model.fragment.ArticleLink;
import org.healthystyle.article.model.fragment.FragmentImage;
import org.healthystyle.article.model.fragment.Order;
import org.healthystyle.article.model.fragment.Quote;
import org.healthystyle.article.model.fragment.Roll;
import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.web.dto.fragment.ArticleLinkDto;
import org.healthystyle.article.web.dto.fragment.FragmentImageDto;
import org.healthystyle.article.web.dto.fragment.OrderDto;
import org.healthystyle.article.web.dto.fragment.QuoteDto;
import org.healthystyle.article.web.dto.fragment.RollDto;
import org.healthystyle.article.web.dto.fragment.text.TextDto;
import org.healthystyle.article.web.dto.mapper.text.TextMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { ArticleLinkMapper.class,
		FragmentImageMapper.class, QuoteMapper.class, RollMapper.class, TextMapper.class })
public interface OrderMapper {
	@SubclassMappings(value = { @SubclassMapping(source = ArticleLink.class, target = ArticleLinkDto.class),
			@SubclassMapping(source = Quote.class, target = QuoteDto.class),
			@SubclassMapping(source = Roll.class, target = RollDto.class),
			@SubclassMapping(source = FragmentImage.class, target = FragmentImageDto.class),
			@SubclassMapping(source = Text.class, target = TextDto.class) })
	OrderDto toDto(Order order);
}
