package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.CureEntity;

import java.util.List;

@Repository
public interface CureRepository extends JpaRepository<CureEntity, Integer> {
    @Query("SELECT c.activeSubstanceEntity FROM CureEntity c WHERE c.diseaseEntity.id = :diseaseId")
    List<ActiveSubstanceEntity> findActiveSubstancesByDiseaseId(@Param("diseaseId") Integer diseaseId);
}
