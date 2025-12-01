package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.RoleDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RoleDtoInser;
import com.iwEmailSender.iwemailsender.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("")
    public ResponseEntity<List<RoleDto>> findAll() {
        List<RoleDto> list= roleService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id) {
        RoleDto roleDto= roleService.findById(id);
        return new ResponseEntity<>(roleDto,HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<String> addRole(@RequestBody RoleDtoInser roleDtoInser){
            roleService.addRole(roleDtoInser);
        return new ResponseEntity<>("Successfully added role", HttpStatus.CREATED);
    }
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID uuid){
            roleService.deleteRole(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
