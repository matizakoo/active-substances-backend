package pl.engineer.active.substances.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.ActiveSubstanceConflictDTO;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesConflictEntity;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.repository.ActiveSubstancesConflictRepository;
import pl.engineer.active.substances.repository.ActiveSubstancesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActiveSubstancesConflictService {
    @Autowired
    private ActiveSubstancesConflictRepository activeSubstancesConflictRepository;
    @Autowired
    private ActiveSubstancesService activeSubstancesService;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;
    @Transactional
    public void save(ActiveSubstancesConflictEntity activeSubstancesConflictEntity) {
        activeSubstancesConflictRepository.save(activeSubstancesConflictEntity);
    }
    public boolean isConflict(ActiveSubstancesConflictEntity activeSubstancesConflictEntity) {
        return activeSubstancesConflictRepository.existsBySubstancjaPair(activeSubstancesConflictEntity.getSubstance(),
                activeSubstancesConflictEntity.getConflictingSubstance());
    }

    public List<ActiveSubstanceConflictDTO> findAll() {
        List<ActiveSubstanceConflictDTO> activeSubstanceConflictDTOList = new ArrayList<>();
        for(ActiveSubstancesConflictEntity entity: activeSubstancesConflictRepository.findAll()) {
            ActiveSubstanceConflictDTO activeSubstanceConflictDTO = ActiveSubstanceConflictDTO.builder()
                    .activesubstanceDTO(activeSubstancesMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(entity.getSubstance()))
                    .activesubstanceDTO2(activeSubstancesMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(entity.getConflictingSubstance()))
                    .build();
            activeSubstanceConflictDTOList.add(activeSubstanceConflictDTO);
        }
        return activeSubstanceConflictDTOList;
    }

    public String createConflict(ActiveSubstanceConflictDTO activeSubstanceConflictDTO) {
        ActiveSubstancesConflictEntity activeSubstancesConflictEntity = null;
        String info = "Wykluczające się czynniki aktywne dla " + activeSubstanceConflictDTO.getActivesubstanceDTO().getName() + " istniały wraz z: ";
        StringBuilder stringBuilder = new StringBuilder();
        for(ActiveSubstanceDTO e: activeSubstanceConflictDTO.getActiveSubstanceDTOList()) {
            if (activeSubstanceConflictDTO.getActivesubstanceDTO().getId().equals(e.getId()))
                continue;
            activeSubstancesConflictEntity = new ActiveSubstancesConflictEntity(
                    activeSubstancesService.findActiveSubstanceById(activeSubstanceConflictDTO.getActivesubstanceDTO().getId()),
                    activeSubstancesService.findActiveSubstanceById(e.getId()));
            if(!isConflict(activeSubstancesConflictEntity)) {
                save(activeSubstancesConflictEntity);
            } else {
                stringBuilder.append(e.getName() + ", ");
            }
        }
        if(stringBuilder.isEmpty())
            return null;
        else
            return info + stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
    }

    public void deleteConflict(Integer id, Integer id2) {
        activeSubstancesConflictRepository.deleteConflictBySubstanceIds(id, id2);
    }
}
