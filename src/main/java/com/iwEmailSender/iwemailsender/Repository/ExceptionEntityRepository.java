package com.iwEmailSender.iwemailsender.Repository;


import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExceptionEntityRepository extends JpaRepository<ExceptionEntity,Long> {
    @Query("select a from ExceptionEntity as a where a.id_job =:id")
    ExceptionEntity findByIdOfJob(@Param("id") Long id);
    @Query("select count(a) from ExceptionEntity as a where a.id_job=:id")
    Integer numOfExceptionsWithSameIdJob(@Param("id")Long id);

    @Query("SELECT j FROM ExceptionEntity j WHERE j.id_job = :id ORDER BY j.dateOfException DESC")
    List<ExceptionEntity> findJobByIdOrderByDateSendDesc(@Param("id") Long id);

    @Query("select count(j) from ExceptionEntity as j")
    int haselements();
    boolean existsByMessage(String message);

    boolean existsById(Long id);
//    ExceptionEntity findById(Long id);
}
