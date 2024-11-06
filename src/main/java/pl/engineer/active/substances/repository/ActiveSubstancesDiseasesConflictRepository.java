package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.ActiveSubstancesDiseasesConflictEntity;

@Repository
public interface ActiveSubstancesDiseasesConflictRepository extends JpaRepository<ActiveSubstancesDiseasesConflictEntity, Integer> {
    boolean existsBySubstanceId(Integer activeSubstanceId);
}
