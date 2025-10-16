package com.iwEmailSender.iwemailsender.Repository;


import com.iwEmailSender.iwemailsender.Model.Repetision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepetisionRepository extends JpaRepository<Repetision,Long> {
    Repetision findByRepetisionName(String repetisionName);
    boolean existsByRepetisionName(String repetisionName);
    boolean existsById(Long id);
}
