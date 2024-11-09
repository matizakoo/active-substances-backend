package pl.engineer.active.substances.service;

import jakarta.transaction.Transactional;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.ActiveSubstanceConflictDTO;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.ActiveSubstancesDiseasesConflictDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesDiseasesConflictEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.exception.ActiveSubstanceException;
import pl.engineer.active.substances.exception.ActiveSubstancesDiseasesException;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.mapper.DiseaseMapper;
import pl.engineer.active.substances.repository.ActiveSubstancesDiseasesConflictRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActiveSubstancesDiseasesConflictService {
    @Autowired
    private ActiveSubstancesDiseasesConflictRepository activeSubstancesDiseasesConflictRepository;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private DiseaseMapper diseaseMapper;


    @Transactional
    public void save(ActiveSubstancesDiseasesConflictEntity activeSubstancesDiseasesConflictEntity) {
        activeSubstancesDiseasesConflictRepository.save(activeSubstancesDiseasesConflictEntity);
    }

    public void createActiveSubstancesDiseasesConflict(ActiveSubstancesDiseasesConflictDTO activeSubstancesDiseasesConflictDTO) {
        isConflictExists(activeSubstancesDiseasesConflictDTO);
        ActiveSubstancesDiseasesConflictEntity activeSubstancesDiseasesConflictEntity = null;
        for(DiseaseDTO e: activeSubstancesDiseasesConflictDTO.getDiseaseDTO()) {
            activeSubstancesDiseasesConflictEntity = new ActiveSubstancesDiseasesConflictEntity(
                    activeSubstancesMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(activeSubstancesDiseasesConflictDTO.getActivesubstanceDTO()),
                    diseaseService.getDiseaseByName(e.getName()));
            save(activeSubstancesDiseasesConflictEntity);
        }
    }

    public List<ActiveSubstancesDiseasesConflictDTO> getConflictsGroupedBySubstance() {
        List<ActiveSubstancesDiseasesConflictEntity> conflicts = activeSubstancesDiseasesConflictRepository.findAll();

        Map<ActiveSubstanceEntity, List<DiseaseDTO>> groupedBySubstance = conflicts.stream()
                .collect(Collectors.groupingBy(
                        ActiveSubstancesDiseasesConflictEntity::getSubstance,
                        Collectors.mapping(
                                conflict -> DiseaseDTO.builder()
                                        .id(conflict.getDiseaseEntity().getId())
                                        .name(conflict.getDiseaseEntity().getName())
                                        .description(conflict.getDiseaseEntity().getDescription())
                                        .build(),
                                Collectors.toList()
                        )
                ));

        return groupedBySubstance.entrySet().stream()
                .map(entry -> {
                    ActiveSubstanceDTO substanceDTO = ActiveSubstanceDTO.builder()
                            .id(entry.getKey().getId())
                            .name(entry.getKey().getName())
                            .pregnance(entry.getKey().getPregnance())
                            .dosage(entry.getKey().getDosage())
                            .description(entry.getKey().getDescription())
                            .build();
                    return new ActiveSubstancesDiseasesConflictDTO(substanceDTO, entry.getValue());
                })
                .collect(Collectors.toList());
    }

    private void isConflictExists(ActiveSubstancesDiseasesConflictDTO activeSubstancesDiseasesConflictDTO) {
        for(DiseaseDTO diseaseDTO: activeSubstancesDiseasesConflictDTO.getDiseaseDTO()) {
            if(activeSubstancesDiseasesConflictRepository.existsByDiseaseIdAndActiveSubstanceId(diseaseDTO.getId(),
                    activeSubstancesDiseasesConflictDTO.getActivesubstanceDTO().getId())) {
                throw new ActiveSubstanceException("Czynnik aktywny jest już powiązany z chorobą " + diseaseDTO.getName());
            }

        }

        if(activeSubstancesDiseasesConflictRepository.existsBySubstanceId(activeSubstancesDiseasesConflictDTO.getActivesubstanceDTO().getId())) {
            throw new ActiveSubstanceException("Czynnik aktywny został już dodany");
        }
    }

    public List<DiseaseEntity> findConflictingDiseasesBySubstances(List<ActiveSubstanceEntity> substances) {
        return activeSubstancesDiseasesConflictRepository.findConflictingDiseasesBySubstances(substances);
    }
}
