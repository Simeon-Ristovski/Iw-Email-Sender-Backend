package com.iwEmailSender.iwemailsender.Service;

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
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class AccountService implements UserDetailsService {
    private static final Logger logger= LoggerFactory.getLogger(AccountService.class);
    private final HttpSession session;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public AccountService(HttpSession session, AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.session = session;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
                if(accountRoleDtoInsert.getFirstName().isEmpty()){
                    logger.error("Field for name cannot be empty!");
                    throw new BadRequestException("Field for name cannot be empty!");
                }
                if(accountRoleDtoInsert.getLastName().isEmpty()){
                    logger.error("Field for last name cannot be empty!");
                    throw new BadRequestException("Field for last name cannot be empty!");
                }
                if(accountRoleDtoInsert.getPassword().isEmpty()){
                    logger.error("Field for password cannot be empty!");
                    throw new BadRequestException("Field for password cannot be empty!");
                }

                Account account = new Account();
                account.setEmail(accountRoleDtoInsert.getEmail());
                account.setFirstName(accountRoleDtoInsert.getFirstName());
                account.setLastName(accountRoleDtoInsert.getLastName());
                account.setPassword(passwordEncoder.encode(accountRoleDtoInsert.getPassword()));
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
                    if (!role.getListOfAccounts().contains(account)) {
                        role.getListOfAccounts().add(account);
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
                                if (!role.getListOfAccounts().contains(account)) {
                                    role.getListOfAccounts().add(account);
                                }
                                account.getRoles().add(role);
                            } else {
                                logger.error("Account already have that role!");
                                throw new AlreadyExistsException("Account already have that role!");
                            }

                        } else {
                            logger.error("The role is not in the Database!");
                            throw new ResourceNotFoundException("The role is not in the Database!");
                        }

                    }
                }

                logger.info("Successfully added account!");

                accountRepository.save(account);
            } else {
                logger.error("Email is not valid!");
                throw new IllegalArgumentException("Email is not valid!");
            }

        } else {
            logger.error("Email already in use!");
            throw new AlreadyExistsException("Email already in use!");
        }
    }
    public void addAccountToBase(AccountDtoInset accountDtoInset) throws BadRequestException {
        if (!accountRepository.existsAccountByEmail(accountDtoInset.getEmail())) {
            if (EmailValidator.getInstance().isValid(accountDtoInset.getEmail()) && !accountDtoInset.getEmail().isEmpty()) {
                if(accountDtoInset.getFirstName().isEmpty()){
                    logger.error("Field for name cannot be empty!");
                    throw new BadRequestException("Field for name cannot be empty!");
                }
                if(accountDtoInset.getLastName().isEmpty()){
                    logger.error("Field for last name cannot be empty!");
                    throw new BadRequestException("Field for last name cannot be empty!");
                }
                if(accountDtoInset.getPassword().isEmpty()){
                    logger.error("Field for password cannot be empty!");
                    throw new BadRequestException("Field for password cannot be empty!");
                }
                Account account = AccountMapper.INSTANCE.mapDtoInsertToAccount(accountDtoInset); // Mapping from AccountDtoInsert to Account
                account.setUuid(UUID.randomUUID());
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                account.setCreatedAt(LocalDateTime.now());
                account.setCreatedBy(accountDtoInset.getEmail());
                account.setModifyAt(LocalDateTime.now());
                account.setModifyBy(accountDtoInset.getEmail());
                Role role = roleRepository.findByRoleName("USER");
                if (account.getRoles() == null) {
                    account.setRoles(new ArrayList<>());
                }
                if (!role.getListOfAccounts().contains(account)) {
                    role.getListOfAccounts().add(account);
                }
                account.getRoles().add(role);
                logger.info("Account added successfully!");
                accountRepository.save(account);
            } else {
                logger.error("Email is not valid!");
                throw new IllegalArgumentException("Email is not valid!");
            }

        } else {
            logger.error("Email already in use!");
            throw new AlreadyExistsException("Email already in use!");
        }

    }
    public void addRoleToAccount(UUID id_acc,RoleDtoInser roleDtoInser ) {
        Account account = accountRepository.findByUuid(id_acc);
        Role role = RoleMapper.INSTANCE.mapDtoToRole(roleDtoInser);
        if (roleRepository.existsByRoleName(role.getRoleName())) {
            Role role1 = roleRepository.findByRoleName(role.getRoleName());
            if (!account.getRoles().contains(role1)) {
                if (!account.getRoles().contains(role1)) {
                    account.getRoles().add(role1);
                }
                if (!role1.getListOfAccounts().contains(account)) {
                    role1.getListOfAccounts().add(account);
                }

                logger.info("Role added successfully to account!");

                accountRepository.save(account);
            } else {
                logger.error("Account already have that role!");
                throw new AlreadyExistsException("Account already have that role!");
            }
        } else {
            logger.error("The role is not in the Database!");
            throw new ResourceNotFoundException("The role is not in the Database!");
        }
    }
    public void removeRoleToAccount(UUID id_acc,RoleDtoInser roleDtoInser ) {
        Account account = accountRepository.findByUuid(id_acc);
        Role role = RoleMapper.INSTANCE.mapDtoToRole(roleDtoInser);
        if (roleRepository.existsByRoleName(role.getRoleName())) {
            Role role1 = roleRepository.findByRoleName(role.getRoleName());
            if (account.getRoles().contains(role1)) {
                account.getRoles().remove(role1);
                if (role1.getListOfAccounts().contains(account)) {
                    role1.getListOfAccounts().remove(account);
                }
                logger.info("Role removed successfully from account!");
                accountRepository.save(account);
            } else {
                logger.error("Account doesn't that role!");
                throw new AlreadyExistsException("Account doesn't have that role!");
            }
        } else {
            logger.error("The role is not in the Database!");
            throw new ResourceNotFoundException("The role is not in the Database!");
        }
    }
    public void editAccount(UUID uuid, AccountDtoInset accountDtoInset) {
        if (accountRepository.existsByUuid(uuid)) {
            Account editAcc = accountRepository.findByUuid(uuid);
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
                    logger.error("Email is used for another user!");
                    throw new AlreadyExistsException("Email is used for another user!");
                }
            }
            if (!accountDtoInset.getPassword().equals(editAcc.getPassword())) {
                editAcc.setPassword(passwordEncoder.encode(accountDtoInset.getPassword()));
            }
            logger.info("Account successfully edited!");
            accountRepository.save(editAcc);
        } else {
            logger.error("Account not found in Database!");
            throw new ResourceNotFoundException("Account not found in Database!");
        }
    }
    public void deleteAccount(Long id) {
        if (accountRepository.existsAccountById(id)) {
            Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account can't be found!"));
            account.getRoles().forEach(role -> role.getListOfAccounts().remove(account));
            account.getRoles().clear();
            logger.info("Account successfully deleted!");
            accountRepository.delete(account);
        } else {
            logger.error("Account can't be found!");
            throw new ResourceNotFoundException("Account can't be found!");
        }
    }
    public void deleteAllAccounts() {
        if (!accountRepository.findAll().isEmpty()) {
            for (Account accountTemp : accountRepository.findAll()) {
                Account account = accountRepository.findById(accountTemp.getId()).orElseThrow(() -> new ResourceNotFoundException("Account can't be found!"));
                account.getRoles().forEach(role -> role.getListOfAccounts().remove(account));
                account.getRoles().clear();
                logger.info("Deleted all accounts!");
                accountRepository.delete(account);
            }
        } else {
            logger.error("No accounts in Database!");
            throw new ResourceNotFoundException("No accounts in Database!");
        }
    }
}
