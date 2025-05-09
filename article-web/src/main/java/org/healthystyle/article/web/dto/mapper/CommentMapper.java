package org.healthystyle.article.web.dto.mapper;

import org.healthystyle.article.model.Comment;
import org.healthystyle.article.web.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
	CommentDto toDto(Comment comment);
}
