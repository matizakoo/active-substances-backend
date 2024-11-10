package pl.engineer.active.substances.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.exception.ActiveSubstanceException;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.repository.ActiveSubstancesConflictRepository;
import pl.engineer.active.substances.repository.ActiveSubstancesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActiveSubstancesService {
    @Autowired
    private ActiveSubstancesRepository activeSubstancesRepository;
    @Autowired
    private ActiveSubstancesConflictRepository activeSubstancesConflictRepository;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;
    @Autowired
    private ActiveSubstancesDiseasesConflictService activeSubstancesDiseasesConflictService;


    @Transactional
    public void save(ActiveSubstanceEntity activeSubstanceEntity) {
        activeSubstancesRepository.save(activeSubstanceEntity);
    }

    public void addNewActiveSubstance(ActiveSubstanceDTO activeSubstanceDTO) {
        ActiveSubstanceEntity activeSubstanceEntityRep = activeSubstancesRepository.findByName(activeSubstanceDTO.getName());
        if (activeSubstanceEntityRep != null) {
            throw new ActiveSubstanceException("Czynnik aktywny istnieje");
        }
        save(activeSubstancesMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(activeSubstanceDTO));
    }

    public List<ActiveSubstanceDTO> getAllActiveSubstances() {
        return activeSubstancesRepository.findAll().stream()
                .map(entity -> new ActiveSubstanceDTO(
                        entity.getId(),
                        entity.getName(),
                        entity.getPregnance(),
                        entity.getDosage(),
                        entity.getDescription()
                ))
                .collect(Collectors.toList());
    }

    public ActiveSubstanceEntity findActiveSubstanceById(Integer id) {
        return activeSubstancesRepository.findById(id).get();
    }

    public void deleteActiveSubstance(Integer id) {
        ActiveSubstanceEntity activeSubstanceEntity = activeSubstancesRepository.findById(id).get();
        activeSubstancesDiseasesConflictService.deleteByIdSubstance(id);
        activeSubstancesConflictRepository.deleteBySubstanceId(id);
        activeSubstancesRepository.delete(activeSubstanceEntity);
    }
}
