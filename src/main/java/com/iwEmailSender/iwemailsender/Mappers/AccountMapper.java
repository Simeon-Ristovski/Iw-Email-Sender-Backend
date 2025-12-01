package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.AccountDto;
import com.iwEmailSender.iwemailsender.Dto.Input.AccountDtoInset;
import com.iwEmailSender.iwemailsender.Dto.Output.RepetitionDto;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.Repetition;
import com.iwEmailSender.iwemailsender.Model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountDto mapAccountToDto(Account account);
    default List<String> map(List<Role> roles){
        return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
    }
    Account mapDtoInsertToAccount(AccountDtoInset accountDtoInset);
    default Repetition map(RepetitionDto repetitionDto) {
        if (repetitionDto == null) return null;
        Repetition r = new Repetition();
        r.setRepetitionName(repetitionDto.getRepetitionName());
        return r;
    }
}
