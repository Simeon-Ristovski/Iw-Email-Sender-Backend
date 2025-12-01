package com.iwEmailSender.iwemailsender.Repository;

import com.iwEmailSender.iwemailsender.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a JOIN fetch a.roles r WHERE r.roleName = 'ADMINISTRATOR'")
    List<Account> findAllAdministrators();
    boolean existsByUuid(UUID uuid);
    Account findByUuid(UUID uuid);
    boolean existsAccountByEmail(String email);
    Optional<Account> findByEmail(String email);
    boolean existsAccountById(Long id);
}
