package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.PatientDiseaseSubstanceEntity;
import pl.engineer.active.substances.entity.UserEntity;

@Repository
public interface PatientDiseaseRepository extends JpaRepository<PatientDiseaseSubstanceEntity, Integer> {
    @Modifying
    @Query("DELETE FROM PatientDiseaseSubstanceEntity p WHERE p.patient.id = :patientId AND p.disease.id = :diseaseId")
    void deleteSpecificDiseaseForPatient(@Param("patientId") Integer patientId, @Param("diseaseId") Integer diseaseId);
}
