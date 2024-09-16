package com.atradius.cafe.application.mapper;

import com.atradius.cafe.application.dto.UserPaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper
public interface UserPaymentDTOMapper {

    UserPaymentDTOMapper INSTANCE = Mappers.getMapper(UserPaymentDTOMapper.class);


    @Mappings({
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "totalPaid", target = "paid")
    })
    UserPaymentDTO toUserPaymentDTO(String user, BigDecimal totalPaid);
}
