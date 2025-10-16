package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.EmailJobDto;
import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobDtoInsert;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.Repetision;
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
    default String map(Repetision repetision){
        return repetision==null ? null:repetision.getRepetisionName();
    }
    default String map(Account account) {
        if (account == null) return null;
        return account.getFirstName() + " " + account.getLastName();
    }
    default Repetision mapRepetision(String repetisionStr) {
        if (repetisionStr == null) {
            return null;
        }
        Repetision repetision = new Repetision();
        repetision.setRepetisionName(repetisionStr);
        return repetision;
    }

}
