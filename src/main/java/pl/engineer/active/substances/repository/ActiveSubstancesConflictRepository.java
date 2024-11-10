package pl.engineer.active.substances.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "SELECT c.conflictingSubstance.id FROM ActiveSubstancesConflictEntity c WHERE c.substance = :substance " +
            "UNION " +
            "SELECT c.substance.id FROM ActiveSubstancesConflictEntity c WHERE c.conflictingSubstance = :substance)")
    List<ActiveSubstanceEntity> findConflictingSubstances(@Param("substance") ActiveSubstanceEntity substance);

    @Modifying
    @Transactional
    @Query("DELETE FROM ActiveSubstancesConflictEntity c WHERE " +
            "(c.substance.id = :id1 AND c.conflictingSubstance.id = :id2) OR " +
            "(c.substance.id = :id2 AND c.conflictingSubstance.id = :id1)")
    void deleteConflictBySubstanceIds(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Modifying
    @Transactional
    @Query("DELETE FROM ActiveSubstancesConflictEntity c WHERE c.substance.id = :substanceId OR c.conflictingSubstance.id = :substanceId")
    void deleteBySubstanceId(@Param("substanceId") Integer substanceId);
}
