package org.healthystyle.article.web.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RollElementMapper.class)
public interface RollMapper {

}
