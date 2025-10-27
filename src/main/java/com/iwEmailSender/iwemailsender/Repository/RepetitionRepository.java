package com.iwEmailSender.iwemailsender.Repository;


import com.iwEmailSender.iwemailsender.Model.Repetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepetitionRepository extends JpaRepository<Repetition,Long> {
    Repetition findByRepetitionName(String repetitionName);
    boolean existsByRepetitionName(String repetitionName);
    boolean existsById(Long id);
}
