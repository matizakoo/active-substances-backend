package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesDiseasesConflictEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;

import java.util.List;

@Repository
public interface ActiveSubstancesDiseasesConflictRepository extends JpaRepository<ActiveSubstancesDiseasesConflictEntity, Integer> {
    boolean existsBySubstanceId(Integer activeSubstanceId);
    @Query("SELECT d FROM DiseaseEntity d " +
            "WHERE d.id NOT IN (SELECT ac.diseaseEntity.id FROM ActiveSubstancesDiseasesConflictEntity ac WHERE ac.substance.id = :substanceId) " +
            "AND d.id NOT IN (SELECT c.diseaseEntity.id FROM CureEntity c WHERE c.activeSubstanceEntity.id = :substanceId)")
    List<DiseaseEntity> findDiseasesNotInRelationWithSubstance(@Param("substanceId") Integer substanceId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM CureEntity c " +
            "WHERE c.diseaseEntity.id = :diseaseId " +
            "AND c.activeSubstanceEntity.id = :activeSubstanceId")
    boolean existsByDiseaseIdAndActiveSubstanceId(@Param("diseaseId") Integer diseaseId,
                                                  @Param("activeSubstanceId") Integer activeSubstanceId);

    @Query("SELECT ac FROM ActiveSubstancesDiseasesConflictEntity ac WHERE ac.diseaseEntity IN :diseases AND ac.substance IN :substances")
    List<ActiveSubstancesDiseasesConflictEntity> findConflicts(@Param("diseases") List<DiseaseEntity> diseases,
                                                               @Param("substances") List<ActiveSubstanceEntity> substances);
    @Query("SELECT DISTINCT c.diseaseEntity FROM ActiveSubstancesDiseasesConflictEntity c WHERE c.substance IN :substances")
    List<DiseaseEntity> findConflictingDiseasesBySubstances(@Param("substances") List<ActiveSubstanceEntity> substances);
}
