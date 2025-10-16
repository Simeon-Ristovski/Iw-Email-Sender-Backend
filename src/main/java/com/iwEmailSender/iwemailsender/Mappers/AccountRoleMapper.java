package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Input.AccountRoleDtoInsert;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountRoleMapper {

    AccountRoleMapper INSTANCE = Mappers.getMapper(AccountRoleMapper.class);
    Account mapDtoInsertToAccount(AccountRoleDtoInsert accountRoleDtoInsert);
    default List<Role> map(List<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return role;
                })
                .collect(Collectors.toList());
    }


}
