package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Input.AccountRoleDtoInsert;
import com.iwEmailSender.iwemailsender.Dto.Input.RoleDtoInser;
import com.iwEmailSender.iwemailsender.Dto.Output.AccountDto;
import com.iwEmailSender.iwemailsender.Dto.Input.AccountDtoInset;
import com.iwEmailSender.iwemailsender.Service.AccountService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<List<AccountDto>> findAll() {
        List<AccountDto> list=accountService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable Long id) {
        AccountDto accountDto= accountService.findById(id);
        return new ResponseEntity<>(accountDto,HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ResponseEntity<String> addAccountToBase(@RequestBody AccountDtoInset accountDtoInset) throws BadRequestException {
        accountService.addAccountToBase(accountDtoInset);
        return new ResponseEntity<>("Added Account!",HttpStatus.CREATED);
    }
    @PostMapping("/add-role")
    public ResponseEntity<String> addAccountToBaseWithRole(@RequestBody AccountRoleDtoInsert accountRoleDtoInsert) throws BadRequestException {
        accountService.addAccountToBaseWithRole(accountRoleDtoInsert);
        return new ResponseEntity<>("Added Account!",HttpStatus.CREATED);
    }
    @PostMapping("/{id_acc}/roles")
    public ResponseEntity<String> addRoleToAccount(@PathVariable Long id_acc, @RequestBody RoleDtoInser roleDtoInser) {
        accountService.addRoleToAccount(id_acc, roleDtoInser);
        return new ResponseEntity<>("Role added to Account!",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editAccount(@PathVariable Long id, @RequestBody AccountDtoInset accountDtoInset) {
        accountService.editAccount(id, accountDtoInset);
        return new ResponseEntity<>("Successfully edited account!",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Deleted Account with id:" + id,HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAccount() {
        accountService.deleteAllAccounts();
        return new ResponseEntity<>("Deleted Accounts",HttpStatus.OK);
    }
}
