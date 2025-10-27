package com.iwEmailSender.iwemailsender.Repository;


import com.iwEmailSender.iwemailsender.Model.EmailJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailJobRepository extends JpaRepository<EmailJob,Long> {
    @Query("select a from EmailJob as a where a.isActive = true")
    List<EmailJob> getEmailJobActive();

    @Query("""
            select a from EmailJob as a 
            left join fetch a.repetition
            left join fetch a.set_by
            left join fetch a.status
            where a.nextSendTime between :timeToSendFrom and :timeToSendTo and a.isActive=true 
""")
    List<EmailJob> findEmailJobsForSendingNow(@Param("timeToSendFrom")LocalDateTime timeToSendFrom, @Param("timeToSendTo")LocalDateTime timeToSendTo);

    boolean existsById(Long id);

}
