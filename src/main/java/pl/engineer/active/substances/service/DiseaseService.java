package pl.engineer.active.substances.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.*;
import pl.engineer.active.substances.entity.*;
import pl.engineer.active.substances.exception.ActiveSubstanceException;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.mapper.DiseaseMapper;
import pl.engineer.active.substances.mapper.PatientMapper;
import pl.engineer.active.substances.mapper.UserMapper;
import pl.engineer.active.substances.repository.ActiveSubstancesDiseasesConflictRepository;
import pl.engineer.active.substances.repository.DiseaseRepository;
import pl.engineer.active.substances.repository.PatientRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiseaseService {
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private ActiveSubstancesDiseasesConflictRepository activeSubstancesDiseasesConflictRepository;
    @Autowired
    private DiseaseMapper diseaseMapper;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PatientRepository patientRepository;;
    @Autowired
    private PatientMapper patientMapper;


    @Transactional
    public void save(DiseaseEntity diseaseEntity) {
        diseaseRepository.save(diseaseEntity);
    }

    public void addNewDisease(DiseaseDTO diseaseDTO) {
        DiseaseEntity diseaseEntityRep = diseaseRepository.findByName(diseaseDTO.getName());
        if (diseaseEntityRep != null) {
            throw new ActiveSubstanceException("Czynnik aktywny istnieje");
        }
        save(diseaseMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(diseaseDTO));
    }
    public List<DiseaseDTO> findAllDiseases() {
        DiseaseDTO diseaseDTO = new DiseaseDTO();
        List<DiseaseEntity> diseaseEntityList;
        List<DiseaseDTO> diseaseDTOList = new ArrayList<>();
        diseaseEntityList = diseaseRepository.findAll();
        for (DiseaseEntity entity: diseaseEntityList) {
            diseaseDTO = diseaseMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(entity);
            diseaseDTO.setActiveSubstancesDTOList(new ArrayList<>());
            if(!entity.getActiveSubstancesDTOList().isEmpty()) {
                for(CureEntity cure: entity.getActiveSubstancesDTOList()) {
                    diseaseDTO.getActiveSubstancesDTOList().add(activeSubstancesMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(cure.getActiveSubstanceEntity()));
                }
            }
            diseaseDTOList.add(diseaseDTO);
        }
        return diseaseDTOList;
    }

    public DiseaseEntity getDiseaseByName(String name) {
        return diseaseRepository.findByName(name);
    }

    public List<DiseaseDTO> getAllDiseasesNotInConflictWithActiveSubstance(Integer id) {
        return diseaseMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(activeSubstancesDiseasesConflictRepository.findDiseasesNotInRelationWithSubstance(id));
    }

    public List<Object[]> findDiseaseWithActiveSubstances(boolean pregnancy, List<Integer> diseaseIds) {
        return diseaseRepository.findDiseaseWithActiveSubstances(pregnancy, diseaseIds);
    }

    public void deleteDisease(Integer id) {
        activeSubstancesDiseasesConflictRepository.deleteByDiseaseId(id);
        DiseaseEntity diseaseEntity = diseaseRepository.findById(id).get();
        diseaseRepository.delete(diseaseEntity);
    }

    public void deleteByDiseaseId(Integer id) {
        activeSubstancesDiseasesConflictRepository.deleteByDiseaseId(id);
    }

    public List<DiseaseDTO> getDiseasesNotExistingForPatient(Integer id) {
         return diseaseMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(diseaseRepository.findDiseasesNotLinkedToPatient(id));
    }
}
