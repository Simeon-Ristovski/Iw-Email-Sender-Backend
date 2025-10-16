package com.iwEmailSender.iwemailsender.Repository;


import com.iwEmailSender.iwemailsender.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
    boolean existsByStatusName(String statusName);
    boolean existsById(Long id);
    Status findByStatusName(String statusName);
//    Status findById(Long id);
}
