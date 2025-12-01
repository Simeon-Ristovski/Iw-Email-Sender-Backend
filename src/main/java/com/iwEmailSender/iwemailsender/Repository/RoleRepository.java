package com.iwEmailSender.iwemailsender.Repository;

import com.iwEmailSender.iwemailsender.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
    boolean existsById(Long id);
    boolean existsByUuid(UUID uuid);
    Role findByUuid(UUID uuid);
}
