package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Configuration.MyPasswordEncoder;
import com.iwEmailSender.iwemailsender.Dto.Input.AccountRoleDtoInsert;
import com.iwEmailSender.iwemailsender.Dto.Input.RoleDtoInser;
import com.iwEmailSender.iwemailsender.Dto.Output.AccountDto;
import com.iwEmailSender.iwemailsender.Dto.Input.AccountDtoInset;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.AlreadyExistsException;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.AccountMapper;
import com.iwEmailSender.iwemailsender.Mappers.RoleMapper;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.Role;
import com.iwEmailSender.iwemailsender.Repository.AccountRepository;
import com.iwEmailSender.iwemailsender.Repository.RoleRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final MyPasswordEncoder myPasswordEncoder;


    public AccountService(AccountRepository accountRepository, RoleRepository roleRepository, MyPasswordEncoder myPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.myPasswordEncoder = myPasswordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(account.isPresent()){
            var userObj = account.get();
            String[] roleNames = userObj.getRoles().stream().map(Role::getRoleName).toArray(String[]::new);
            return User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(roleNames)
                    .build();
        }else {
            throw new UsernameNotFoundException(email);
        }
    }



    public List<AccountDto> findAll() {
        List<AccountDto> accounts = new ArrayList<>();
        for (Account account : accountRepository.findAll()) {
            AccountDto accountDto = AccountMapper.INSTANCE.mapAccountToDto(account);
            accounts.add(accountDto);
        }
        return accounts;
    }

    public AccountDto findById(Long id) {
        return AccountMapper.INSTANCE.mapAccountToDto(accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found in Database!")));
    }

    public void addAccountToBaseWithRole(AccountRoleDtoInsert accountRoleDtoInsert) throws BadRequestException {
        if (!accountRepository.existsAccountByEmail(accountRoleDtoInsert.getEmail())) {
            if (EmailValidator.getInstance().isValid(accountRoleDtoInsert.getEmail()) && !accountRoleDtoInsert.getEmail().isEmpty()) {
//                Account account = AccountRoleMapper.INSTANCE.mapDtoInsertToAccount(accountRoleDtoInsert);// Mapping from AccountDtoInsert to Account
                if(accountRoleDtoInsert.getFirstName().isEmpty()){
                    throw new BadRequestException("Field for name cannot be empty!");
                }
                if(accountRoleDtoInsert.getLastName().isEmpty()){
                    throw new BadRequestException("Field for last name cannot be empty!");
                }
                if(accountRoleDtoInsert.getPassword().isEmpty()){
                    throw new BadRequestException("Field for password cannot be empty!");
                }
                Account account = new Account();
                account.setEmail(accountRoleDtoInsert.getEmail());
                account.setFirstName(accountRoleDtoInsert.getFirstName());
                account.setLastName(accountRoleDtoInsert.getLastName());
                account.setPassword(myPasswordEncoder.bCryptPasswordEncoder(accountRoleDtoInsert.getPassword()));
                account.setUuid(UUID.randomUUID());
                account.setCreatedAt(LocalDateTime.now());
                account.setCreatedBy("SYSTEM");
                account.setModifyAt(LocalDateTime.now());
                account.setModifyBy("SYSTEM");

                if (accountRoleDtoInsert.getRoles().isEmpty()) {
                    Role role = roleRepository.findByRoleName("USER");
                    if (account.getRoles() == null) {
                        account.setRoles(new ArrayList<>());
                    }
                    if (!role.getList_of_accounts().contains(account)) {
                        role.getList_of_accounts().add(account);
                    }
                    account.getRoles().add(role);
                } else {
                    for (String role1 : accountRoleDtoInsert.getRoles()) {
                        if (roleRepository.existsByRoleName(role1)) {
                            Role role = roleRepository.findByRoleName(role1);
                            if (!account.getRoles().contains(role)) {
                                if (account.getRoles() == null) {
                                    account.setRoles(new ArrayList<>());
                                }
                                if (!role.getList_of_accounts().contains(account)) {
                                    role.getList_of_accounts().add(account);
                                }
                                account.getRoles().add(role);
                            } else {
                                throw new AlreadyExistsException("Account already have that role!");
                            }

                        } else {
                            throw new ResourceNotFoundException("The role is not in the Database!");
                        }

                    }
                }
                accountRepository.save(account);
            } else {
                throw new IllegalArgumentException("Email is not valid!");
            }

        } else {
            throw new AlreadyExistsException("Email already in use!");
        }
    }

    public void addAccountToBase(AccountDtoInset accountDtoInset) throws BadRequestException {
        if (!accountRepository.existsAccountByEmail(accountDtoInset.getEmail())) {
            if (EmailValidator.getInstance().isValid(accountDtoInset.getEmail()) && !accountDtoInset.getEmail().isEmpty()) {
                if(accountDtoInset.getFirstName().isEmpty()){
                    throw new BadRequestException("Field for name cannot be empty!");
                }
                if(accountDtoInset.getLastName().isEmpty()){
                    throw new BadRequestException("Field for last name cannot be empty!");
                }
                if(accountDtoInset.getPassword().isEmpty()){
                    throw new BadRequestException("Field for password cannot be empty!");
                }
                Account account = AccountMapper.INSTANCE.mapDtoInsertToAccount(accountDtoInset); // Mapping from AccountDtoInsert to Account
                account.setUuid(UUID.randomUUID());
                account.setPassword(myPasswordEncoder.bCryptPasswordEncoder(account.getPassword()));
                account.setCreatedAt(LocalDateTime.now());
                account.setCreatedBy("SYSTEM");
                account.setModifyAt(LocalDateTime.now());
                account.setModifyBy("SYSTEM");
                Role role = roleRepository.findByRoleName("USER");
                if (account.getRoles() == null) {
                    account.setRoles(new ArrayList<>());
                }
                if (!role.getList_of_accounts().contains(account)) {
                    role.getList_of_accounts().add(account);
                }
                account.getRoles().add(role);
                accountRepository.save(account);
            } else {
                throw new IllegalArgumentException("Email is not valid!");
            }

        } else {
            throw new AlreadyExistsException("Email already in use!");
        }

    }

    public void addRoleToAccount(Long id_acc, RoleDtoInser roleDtoInser) {
        Account account = accountRepository.findById(id_acc).orElseThrow(() -> new ResourceNotFoundException("Account not found in Database!"));
        Role role = RoleMapper.INSTANCE.mapDtoToRole(roleDtoInser);
        if (roleRepository.existsByRoleName(role.getRoleName())) {
            Role role1 = roleRepository.findByRoleName(role.getRoleName());
            if (!account.getRoles().contains(role1)) {
                if (!account.getRoles().contains(role1)) {
                    account.getRoles().add(role1);
                }
                if (!role1.getList_of_accounts().contains(account)) {
                    role1.getList_of_accounts().add(account);
                }
                accountRepository.save(account);
            } else {
                throw new AlreadyExistsException("Account already have that role!");
            }

        } else {
            throw new ResourceNotFoundException("The role is not in the Database!");
        }
    }

    public void editAccount(Long id, AccountDtoInset accountDtoInset) {
        if (accountRepository.existsAccountById(id)) {
            Account editAcc = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found in Database!"));
            editAcc.setModifyAt(LocalDateTime.now());
            editAcc.setModifyBy("SYSTEM"); //Change when session is implemented
            if (!accountDtoInset.getFirstName().equals(editAcc.getFirstName())) {
                editAcc.setFirstName(accountDtoInset.getFirstName());
            }
            if (!accountDtoInset.getLastName().equals(editAcc.getLastName())) {
                editAcc.setLastName(accountDtoInset.getLastName());
            }
            if (!accountDtoInset.getEmail().equals(editAcc.getEmail())) {
                if (!accountRepository.findAll().contains(accountRepository.findByEmail(accountDtoInset.getEmail()))) {
                    editAcc.setEmail(accountDtoInset.getEmail());
                } else {
                    throw new AlreadyExistsException("Email is used for another user!");
                }
            }
            if (!accountDtoInset.getPassword().equals(editAcc.getPassword())) {
                editAcc.setPassword(myPasswordEncoder.bCryptPasswordEncoder(accountDtoInset.getPassword()));
            }
            accountRepository.save(editAcc);
        } else {
            throw new ResourceNotFoundException("Account not found in Database!");
        }
    }

    public void deleteAccount(Long id) {
        if (accountRepository.existsAccountById(id)) {
            Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account can't be found!"));
            account.getRoles().forEach(role -> role.getList_of_accounts().remove(account));
            account.getRoles().clear();
            accountRepository.delete(account);
        } else {
            throw new ResourceNotFoundException("Account can't be found!");
        }
    }


    public void deleteAllAccounts() {
        if (!accountRepository.findAll().isEmpty()) {
            for (Account account : accountRepository.findAll()) {
                deleteAccount(account.getId());
            }
        } else {
            throw new ResourceNotFoundException("No accounts in Database!");
        }
    }


}
