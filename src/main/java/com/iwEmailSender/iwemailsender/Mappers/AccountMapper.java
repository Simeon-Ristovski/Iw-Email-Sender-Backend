package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.AccountDto;
import com.iwEmailSender.iwemailsender.Dto.Input.AccountDtoInset;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.Repetision;
import com.iwEmailSender.iwemailsender.Model.Role;
import com.iwEmailSender.iwemailsender.Dto.Output.RepetisionDto;
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
    default Repetision map(RepetisionDto repetisionDto) {
        if (repetisionDto == null) return null;
        Repetision r = new Repetision();
        r.setRepetisionName(repetisionDto.getRepetisionName());
        return r;
    }


}
