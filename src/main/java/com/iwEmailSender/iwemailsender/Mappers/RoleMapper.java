package com.iwEmailSender.iwemailsender.Mappers;

import com.iwEmailSender.iwemailsender.Dto.Output.RoleDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RoleDtoInser;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Role mapDtoToRole(RoleDtoInser roleDtoInser);
    RoleDto mapRoleToDto(Role role);
    default List<String>map(List<Account> accounts){
        return accounts.stream().map(Account::toString).collect(Collectors.toList());
    }
}
