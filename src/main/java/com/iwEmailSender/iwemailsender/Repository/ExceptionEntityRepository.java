package com.iwEmailSender.iwemailsender.Repository;


import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExceptionEntityRepository extends JpaRepository<ExceptionEntity,Long> {

    ExceptionEntity findByUuid(UUID uuid);
    @Query("select e from ExceptionEntity as e where e.id_job= :id order by e.dateOfException desc ")
    List<ExceptionEntity> findTopById_jobOrderByDateOfExceptionDesc(@Param("id")Long id );
}
