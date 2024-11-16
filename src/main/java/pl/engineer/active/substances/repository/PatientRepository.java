package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.engineer.active.substances.entity.PatientEntity;

import java.util.Optional;

import static pl.engineer.active.substances.controller.advice.Endpoint.*;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Integer> {
    Optional<PatientEntity> findById(Integer id);
    Optional<PatientEntity> findByUniqueId(String id);
}
