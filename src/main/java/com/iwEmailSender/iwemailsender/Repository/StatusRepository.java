package com.iwEmailSender.iwemailsender.Repository;

import com.iwEmailSender.iwemailsender.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
    boolean existsByStatusName(String statusName);
    boolean existsById(Long id);
    boolean existsByUuid(UUID uuid);
    Status findByUuid(UUID uuid);
    Status findByStatusName(String statusName);
}
