package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesConflictEntity;

import java.util.List;

@Repository
public interface ActiveSubstancesConflictRepository extends JpaRepository<ActiveSubstancesConflictEntity, Integer> {
    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END " +
            "FROM ActiveSubstancesConflictEntity w " +
            "WHERE (w.substance = :substance AND w.conflictingSubstance = :conflictingSubstance) " +
            "   OR (w.substance = :conflictingSubstance AND w.conflictingSubstance = :substance)")
    boolean existsBySubstancjaPair(@Param("substance") ActiveSubstanceEntity substance,
                                   @Param("conflictingSubstance") ActiveSubstanceEntity conflictingSubstance);

    @Query("SELECT ac FROM ActiveSubstancesConflictEntity ac " +
            "WHERE (ac.substance IN :substances AND ac.conflictingSubstance IN :substances) " +
            "OR (ac.conflictingSubstance IN :substances AND ac.substance IN :substances)")
    List<ActiveSubstancesConflictEntity> findConflictsBetweenSubstances(@Param("substances") List<ActiveSubstanceEntity> substances);

    @Query("SELECT ac FROM ActiveSubstancesConflictEntity ac")
    List<ActiveSubstancesConflictEntity> findAllConflicts();

    @Query("SELECT a FROM ActiveSubstanceEntity a WHERE a.id IN (" +
            "SELECT c.conflictingSubstance.id FROM ActiveSubstancesConflictEntity c WHERE c.substance IN :substances " +
            "UNION " +
            "SELECT c.substance.id FROM ActiveSubstancesConflictEntity c WHERE c.conflictingSubstance IN :substances) " +
            "AND a.pregnance = :pregnancy")
    List<ActiveSubstanceEntity> findConflictingSubstances(@Param("substances") List<ActiveSubstanceEntity> substances,
                                                          @Param("pregnancy") Boolean pregnancy);
}
