package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT p.userEntity FROM PatientDiseaseSubstanceEntity p WHERE p.patient.id = :patientId")
    UserEntity findUserByPatientId(@Param("patientId") Integer patientId);
}
