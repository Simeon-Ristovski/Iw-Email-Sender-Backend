package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetisionDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RepetisionDtoInsert;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.Repetision;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RepetisionMapper {

    RepetisionMapper INSTANCE = Mappers.getMapper(RepetisionMapper.class);

    RepetisionDto mapRepetisionToDto(Repetision repetision);
    Repetision mapDtoToRepetision(RepetisionDtoInsert repetisionDtoInsert);

    default List<String> map(List<EmailJob> emailJob){
        return emailJob.stream().map(EmailJob::toString).collect(Collectors.toList());
    }
}
