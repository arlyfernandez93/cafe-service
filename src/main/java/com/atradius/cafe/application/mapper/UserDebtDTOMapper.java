package com.atradius.cafe.application.mapper;

import com.atradius.cafe.application.dto.UserDebtDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper
public interface UserDebtDTOMapper {

    UserDebtDTOMapper INSTANCE = Mappers.getMapper(UserDebtDTOMapper.class);

    @Mappings({
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "outstandingAmount", target = "outstandingAmount")
    })
    UserDebtDTO toUserDebtDTO(String user, BigDecimal outstandingAmount);

}
