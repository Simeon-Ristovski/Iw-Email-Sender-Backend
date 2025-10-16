package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.ExeceptionEntityDto;
import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExceptionEntityMapper {

    ExceptionEntityMapper INSTANCE= Mappers.getMapper(ExceptionEntityMapper.class);
    ExeceptionEntityDto mapExceptionEntityToDto(ExceptionEntity exception);
}
