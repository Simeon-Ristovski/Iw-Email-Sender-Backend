package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.EmailJobDto;
import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobDtoInsert;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.Repetition;
import com.iwEmailSender.iwemailsender.Model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmailJobMapper {

    EmailJobMapper INSTANCE = Mappers.getMapper(EmailJobMapper.class);

    EmailJobDto mapEmailJobToDto(EmailJob emailJob);
    EmailJob mapDtoInsertToEmailJob(EmailJobDtoInsert emailJobDto);
    default String map(Status status){
        return status==null ? null:status.getStatusName();
    }
    default String map(Repetition repetition){
        return repetition==null ? null:repetition.getRepetitionName();
    }
    default String map(Account account) {
        if (account == null) return null;
        return account.getFirstName() + " " + account.getLastName();
    }
    default Repetition mapRepetition(String repetitionStr) {
        if (repetitionStr == null) {
            return null;
        }
        Repetition repetition = new Repetition();
        repetition.setRepetitionName(repetitionStr);
        return repetition;
    }

}
