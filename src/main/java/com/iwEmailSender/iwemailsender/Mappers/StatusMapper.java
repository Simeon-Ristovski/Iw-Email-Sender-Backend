package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.StatusDto;
import com.iwEmailSender.iwemailsender.Dto.Input.StatusDtoInsert;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    StatusDto mapStatusToDto(Status status);
    Status mapDtoToStatus(StatusDtoInsert dtoInsert);

    default List<String> map(List<EmailJob> emailJobs){
        return emailJobs.stream().map(EmailJob::toString).collect(Collectors.toList());
    }
}
