package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetitionDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RepetitionDtoInsert;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.Repetition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RepetitionMapper {

    RepetitionMapper INSTANCE = Mappers.getMapper(RepetitionMapper.class);

    RepetitionDto mapRepetitionToDto(Repetition repetition);
    Repetition mapDtoToRepetition(RepetitionDtoInsert repetitionDtoInsert);

    default List<String> map(List<EmailJob> emailJob){
        return emailJob.stream().map(EmailJob::toString).collect(Collectors.toList());
    }
}
