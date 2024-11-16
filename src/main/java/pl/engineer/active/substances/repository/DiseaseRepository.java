package pl.engineer.active.substances.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Integer> {
    DiseaseEntity findByName(String name);

    @Query("SELECT d, a FROM DiseaseEntity d " +
            "JOIN d.activeSubstancesDTOList c " +
            "JOIN c.activeSubstanceEntity a " +
            "WHERE d.id IN :diseaseIds AND a.pregnance = :pregnance")
    List<Object[]> findDiseaseWithActiveSubstances(@Param("pregnance") boolean pregnance, @Param("diseaseIds") List<Integer> diseaseIds);
    @Modifying
    @Transactional
    @Query("DELETE FROM ActiveSubstancesDiseasesConflictEntity c WHERE c.diseaseEntity.id = :diseaseId")
    void deleteByDiseaseId(@Param("diseaseId") Integer diseaseId);

    @Query("SELECT d FROM DiseaseEntity d " +
            "WHERE d.id NOT IN (" +
            "    SELECT p.disease.id " +
            "    FROM PatientDiseaseSubstanceEntity p " +
            "    WHERE p.patient.id = :patientId" +
            ")")
    List<DiseaseEntity> findDiseasesNotLinkedToPatient(@Param("patientId") Integer patientId);
}
