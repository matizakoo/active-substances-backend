package pl.engineer.active.substances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.engineer.active.substances.entity.CureEntity;

@Repository
public interface CureRepository extends JpaRepository<CureEntity, Integer> {
}