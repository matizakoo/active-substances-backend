package pl.engineer.active.substances.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.entity.CureEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.exception.ActiveSubstanceException;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.mapper.DiseaseMapper;
import pl.engineer.active.substances.repository.DiseaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class DiseaseService {
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private DiseaseMapper diseaseMapper;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;

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
        System.out.println(diseaseDTOList);
        return diseaseDTOList;
    }

    public DiseaseEntity getDisease(String name) {
        return diseaseRepository.findByName(name);
    }
}
