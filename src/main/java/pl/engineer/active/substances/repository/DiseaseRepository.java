package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.DiseaseEntity;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Integer> {
}
