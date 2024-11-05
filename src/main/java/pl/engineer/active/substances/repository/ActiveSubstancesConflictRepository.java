package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesConflictEntity;

@Repository
public interface ActiveSubstancesConflictRepository extends JpaRepository<ActiveSubstancesConflictEntity, Integer> {
    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END " +
            "FROM ActiveSubstancesConflictEntity w " +
            "WHERE (w.substance = :substance AND w.conflictingSubstance = :conflictingSubstance) " +
            "   OR (w.substance = :conflictingSubstance AND w.conflictingSubstance = :substance)")
    boolean existsBySubstancjaPair(@Param("substance") ActiveSubstanceEntity substance,
                                   @Param("conflictingSubstance") ActiveSubstanceEntity conflictingSubstance);
}
