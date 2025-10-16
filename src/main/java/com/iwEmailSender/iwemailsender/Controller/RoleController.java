package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.RoleDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RoleDtoInser;
import com.iwEmailSender.iwemailsender.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;


    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("")
    public List<RoleDto> findAll(){
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public RoleDto findById(@PathVariable Long id){
        return roleService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<String> addRole(@RequestBody RoleDtoInser roleDtoInser){
            roleService.addRole(roleDtoInser);
        return new ResponseEntity<>("Successfully added role", HttpStatus.CREATED);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
            roleService.deleteRole(id);
        return new ResponseEntity<>("Successfully deleted role", HttpStatus.OK);
    }
}
