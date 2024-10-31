package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;

import java.util.List;

@Repository
public interface ActiveSubstancesRepository extends JpaRepository<ActiveSubstanceEntity, Integer> {
    @Query(value = """
            SELECT DISTINCT asub.*
            FROM active_substance asub
            JOIN cure c1 ON asub.id = c1.active_substance_id
            JOIN disease d1 ON c1.disease_id = d1.id
            WHERE d1.name = :diseaseName
              AND asub.id NOT IN (
                  SELECT sc.conflicting_substance_id
                  FROM substance_conflict sc
                  WHERE sc.substance_id IN :existingSubstances
              )
            """, nativeQuery = true)
    List<ActiveSubstanceEntity> findActiveSubstancesWithoutConflicts(
            @Param("diseaseName") String diseaseName,
            @Param("existingSubstances") List<Integer> existingSubstances
    );

    ActiveSubstanceEntity findByName(String nameOfSubstance);
}
