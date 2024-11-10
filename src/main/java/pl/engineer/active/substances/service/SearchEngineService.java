package pl.engineer.active.substances.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.dto.SearchEngineDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesConflictEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesDiseasesConflictEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.mapper.DiseaseMapper;
import pl.engineer.active.substances.repository.ActiveSubstancesConflictRepository;
import pl.engineer.active.substances.repository.ActiveSubstancesDiseasesConflictRepository;
import pl.engineer.active.substances.repository.ActiveSubstancesRepository;
import pl.engineer.active.substances.repository.CureRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchEngineService {
    @Autowired
    public ActiveSubstancesService searchEngineService;
    @Autowired
    public DiseaseService diseaseService;
    @Autowired
    public ActiveSubstancesDiseasesConflictRepository activeSubstancesDiseasesConflictRepository;
    @Autowired
    public ActiveSubstancesConflictRepository activeSubstancesConflictRepository;
    @Autowired
    public DiseaseMapper diseaseMapper;
    @Autowired
    public ActiveSubstancesMapper activeSubstancesMapper;
    @Autowired
    public ActiveSubstancesService activeSubstancesService;
    @Autowired
    public ActiveSubstancesDiseasesConflictService activeSubstancesDiseasesConflictService;
    @Autowired
    public CureRepository cureRepository;
    @Autowired
    public ActiveSubstancesRepository activeSubstancesRepository;


    public List<DiseaseDTO> searchEngine(SearchEngineDTO searchEngineDTO) {
        DiseaseEntity disease = diseaseMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(searchEngineDTO.getDiseaseModelDTO());
        DiseaseDTO diseaseDTO = searchEngineDTO.getDiseaseModelDTO();

        List<ActiveSubstanceEntity> listOfActiveSubstancesToCureDisease =
                cureRepository.findActiveSubstancesByDiseaseId(disease.getId());

        List<Integer> diseaseIds = searchEngineDTO.getDiseaseModelDTOList().stream()
                .map(DiseaseDTO::getId)
                .collect(Collectors.toList());

        List<ActiveSubstanceEntity> conflictSubstancies = activeSubstancesDiseasesConflictRepository.findConflictingSubstancesByDiseaseIds(diseaseIds);

        listOfActiveSubstancesToCureDisease.removeIf(
                substanceToCure -> conflictSubstancies.stream()
                        .anyMatch(conflictSubstance -> conflictSubstance.getId().equals(substanceToCure.getId()))
        );

        List<ActiveSubstanceEntity> currentActiveSubstances = searchEngineDTO.getActivesubstanceModel().stream()
                .map(activeSubstanceDTO -> activeSubstancesMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(activeSubstanceDTO))
                .collect(Collectors.toList());

        for (ActiveSubstanceEntity currentSubstance : currentActiveSubstances) {
            List<ActiveSubstanceEntity> conflictingSubstances = activeSubstancesConflictRepository
                    .findConflictingSubstances(currentSubstance);
            listOfActiveSubstancesToCureDisease.removeIf(
                    substanceToCure -> conflictingSubstances.stream()
                            .anyMatch(conflictSubstance -> conflictSubstance.getId().equals(substanceToCure.getId()))
            );
        }

        Boolean isPregnant = searchEngineDTO.getPregnance();
        listOfActiveSubstancesToCureDisease.removeIf(
                substanceToCure -> currentActiveSubstances.stream()
                        .anyMatch(currentSubstance -> currentSubstance.getId().equals(substanceToCure.getId()))
                        && (isPregnant && !substanceToCure.getPregnance())
        );

        // Dodatkowy Krok: Usunięcie substancji aktywnych już znajdujących się w `activesubstanceModel`
        List<Integer> activeSubstanceIdsInModel = searchEngineDTO.getActivesubstanceModel().stream()
                .map(ActiveSubstanceDTO::getId)
                .collect(Collectors.toList());

        listOfActiveSubstancesToCureDisease.removeIf(
                substanceToCure -> activeSubstanceIdsInModel.contains(substanceToCure.getId())
        );

        diseaseDTO.setActiveSubstancesDTOList(
                listOfActiveSubstancesToCureDisease.stream()
                        .map(activeSubstancesMapper::mapActiveSubstancesEntityToActiveSubstancesDTO)
                        .collect(Collectors.toList())
        );

        return List.of(diseaseDTO);
    }

}
