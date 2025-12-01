package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.RoleDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RoleDtoInser;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.AlreadyExistsException;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.RoleMapper;
import com.iwEmailSender.iwemailsender.Model.Role;
import com.iwEmailSender.iwemailsender.Repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<RoleDto> findAll(){
        List<RoleDto> list = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            RoleDto roleDto = RoleMapper.INSTANCE.mapRoleToDto(role);
            list.add(roleDto);
        }
        return list;
    }
    public RoleDto findById(Long id){
        return RoleMapper.INSTANCE.mapRoleToDto(roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The role with that id doesn't exist in Database!")));
    }
    public void addRole(RoleDtoInser roleDtoInser){
        if(!roleRepository.existsByRoleName(roleDtoInser.getRoleName())){
            Role role = RoleMapper.INSTANCE.mapDtoToRole(roleDtoInser);
            role.setUuid(UUID.randomUUID());
            roleRepository.save(role);
        }else {
            throw new AlreadyExistsException("The role already exists!");
        }
    }
    public void deleteRole(UUID id){
        if(roleRepository.existsByUuid(id)){
            Role role =roleRepository.findByUuid(id);
            role.getListOfAccounts().clear();
            roleRepository.delete(role);
        }else {
            throw new ResourceNotFoundException("The role doesn't exist in the Database");
        }
    }
}
