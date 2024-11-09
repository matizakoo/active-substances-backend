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



    public List<DiseaseDTO> searchEngine(SearchEngineDTO searchEngineDTO) {
        // Zmapowanie substancji aktywnych dla danej choroby
        // Główna lista z DiseasesDTO
        List<DiseaseDTO> diseaseEntityDTOList = new ArrayList<>();
        List<DiseaseDTO> diseaseEntityDTOList2 = new ArrayList<>();
        List<DiseaseDTO> diseaseEntityDTOList3 = new ArrayList<>();
        List<ActiveSubstanceDTO> activeDto = new ArrayList<>();

        for (DiseaseDTO entity : searchEngineDTO.getDiseaseModelDTO()) {
            DiseaseEntity disease = diseaseMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(entity);
            entity.setActiveSubstancesDTOList(
                    activeSubstancesMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(
                            activeSubstancesService.getAllActiveSubstancesForDiseases(disease)));
            diseaseEntityDTOList.add(entity);
        }

        System.out.println(diseaseEntityDTOList);


        for (DiseaseDTO diseaseDTO : diseaseEntityDTOList) {
            List<ActiveSubstanceEntity> activeSubstanceEntitiesList = new ArrayList<>();
            for (ActiveSubstanceDTO activeSubstanceDTO : diseaseDTO.getActiveSubstancesDTOList()) {
                activeSubstanceEntitiesList.add(activeSubstancesMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(activeSubstanceDTO));
            }
            List<ActiveSubstanceEntity> activeSubstanceEntities = activeSubstancesService.findConflictingSubstances(activeSubstanceEntitiesList, searchEngineDTO.getPregnance());

            List<ActiveSubstanceEntity> filteredList = activeSubstanceEntitiesList.stream()
                    .filter(mainSubstance -> activeSubstanceEntities.stream()
                            .noneMatch(conflictingSubstance ->
                                    conflictingSubstance.getId().equals(mainSubstance.getId())
                            )
                    )
                    .collect(Collectors.toList());

            activeSubstanceEntitiesList.clear();
            activeSubstanceEntitiesList.addAll(filteredList);

            DiseaseDTO diseaseDTO1 = diseaseDTO;
            diseaseDTO1.setActiveSubstancesDTOList(activeSubstancesMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(activeSubstanceEntitiesList));
            diseaseEntityDTOList2.add(diseaseDTO1);
            System.out.println(diseaseEntityDTOList2);
            activeSubstanceEntitiesList = null;
            filteredList = null;
        }

        for(DiseaseDTO diseaseDTO: diseaseEntityDTOList2) {
            List<ActiveSubstanceEntity> activeSubstancesDTOList = new ArrayList<>();
            for(ActiveSubstanceDTO activeSubstanceDTO: diseaseDTO.getActiveSubstancesDTOList()) {
                activeSubstancesDTOList.add(activeSubstancesMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(activeSubstanceDTO));
            }
            // lista skonfliktowanych substancji z chorobami
            List<DiseaseEntity> conflictingDiseases  = activeSubstancesDiseasesConflictService.findConflictingDiseasesBySubstances(activeSubstancesDTOList);

            Set<Integer> conflictingDiseaseIds = conflictingDiseases.stream()
                    .map(DiseaseEntity::getId)
                    .collect(Collectors.toSet());

            List<ActiveSubstanceDTO> activeSubstancesForDisease = diseaseDTO.getActiveSubstancesDTOList();

            List<ActiveSubstanceDTO> nonConflictingSubstances = activeSubstancesForDisease.stream()
                    .filter(activeSubstance ->
                            !conflictingDiseaseIds.contains(activeSubstance.getId())
                    )
                    .collect(Collectors.toList());

            // Przefiltrowane substancje aktywne dla choroby
            activeDto.addAll(nonConflictingSubstances);

            List<ActiveSubstanceDTO> nonConflictingActiveDto = new ArrayList<>(activeDto);

            DiseaseDTO diseaseDTO1 = diseaseDTO;
            diseaseDTO1.setActiveSubstancesDTOList(nonConflictingActiveDto);
            diseaseEntityDTOList3.add(diseaseDTO1);
            activeDto.clear();
        }

        System.out.println(diseaseEntityDTOList3);
        //------
//        Map<Integer, Set<Integer>> conflictMap = new HashMap<>();
//        List<ActiveSubstancesConflictEntity> conflicts = activeSubstancesConflictRepository.findAll();
//
//        for (ActiveSubstancesConflictEntity conflict : conflicts) {
//            conflictMap.computeIfAbsent(conflict.getSubstance().getId(), k -> new HashSet<>()).add(conflict.getConflictingSubstance().getId());
//            conflictMap.computeIfAbsent(conflict.getConflictingSubstance().getId(), k -> new HashSet<>()).add(conflict.getSubstance().getId());
//        }
//        for (int i = 0; i < diseaseEntityDTOList3.size(); i++) {
//            DiseaseDTO disease1 = diseaseEntityDTOList3.get(i);
//            List<ActiveSubstanceDTO> substances1 = disease1.getActiveSubstancesDTOList();
//
//            for (int j = i + 1; j < diseaseEntityDTOList3.size(); j++) {
//                DiseaseDTO disease2 = diseaseEntityDTOList3.get(j);
//                List<ActiveSubstanceDTO> substances2 = disease2.getActiveSubstancesDTOList();
//
//                // Lista do przechowywania skonfliktowanych par między dwiema chorobami
//                List<String> conflictingPairs = new ArrayList<>();
//
//                for (ActiveSubstanceDTO substance1 : substances1) {
//                    Integer id1 = substance1.getId();
//
//                    for (ActiveSubstanceDTO substance2 : substances2) {
//                        Integer id2 = substance2.getId();
//
//                        // Sprawdź, czy substancja z choroby 1 jest w konflikcie z substancją z choroby 2
//                        if (conflictMap.containsKey(id1) && conflictMap.get(id1).contains(id2)) {
//                            conflictingPairs.add(substance1.getName() + " (ID " + id1 + ") <-> " + substance2.getName() + " (ID " + id2 + ")");
//                        }
//                    }
//                }
//
//                // Wyświetlenie skonfliktowanych substancji między dwiema chorobami
//                if (!conflictingPairs.isEmpty()) {
//                    System.out.println("Konflikty między chorobami: " + disease1.getName() + " (ID " + disease1.getId() +
//                            ") i " + disease2.getName() + " (ID " + disease2.getId() + ")");
//                    System.out.println("Skonfliktowane substancje:");
//                    for (String pair : conflictingPairs) {
//                        System.out.println(" - " + pair);
//                    }
//                    System.out.println();
//                }
//            }
//        }
        return diseaseEntityDTOList3;
    }
}
