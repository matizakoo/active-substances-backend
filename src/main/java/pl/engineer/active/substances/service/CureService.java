package pl.engineer.active.substances.service;


import jakarta.transaction.Transactional;
import org.hibernate.engine.internal.UnsavedValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.entity.CureEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.repository.CureRepository;

@Service
public class CureService {
    @Autowired
    private CureRepository cureRepository;
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;


    @Transactional
    public void saveRelation(CureEntity cureEntity) {
        cureRepository.save(cureEntity);
    }

    public void saveRelationList(DiseaseDTO diseaseDTO) {
        DiseaseEntity diseaseEntity = diseaseService.getDisease(diseaseDTO.getName());
        CureEntity cureEntity = null;
        for(ActiveSubstanceDTO e: diseaseDTO.getActiveSubstancesDTOList()) {
            cureEntity = CureEntity.builder()
                    .diseaseEntity(diseaseEntity)
                    .activeSubstanceEntity(activeSubstancesMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(e))
                    .build();
            saveRelation(cureEntity);
        }
    }
}
