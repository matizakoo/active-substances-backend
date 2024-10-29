package pl.engineer.active.substances.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.repository.ActiveSubstancesRepository;

import static pl.engineer.active.substances.controller.advice.Endpoint.*;

@RestController
@RequestMapping(value = DOCTOR + ACTIVE_SUBSTANCES)
public class ActiveSubstancesController {
    @Autowired
    private ActiveSubstancesRepository activeSubstancesRepository;
    @PostMapping
    public ResponseEntity<InfoDTO> createActiveSubstance(@RequestBody ActiveSubstanceEntity activeSubstanceEntity) {
        System.out.println(activeSubstanceEntity.toString());
        activeSubstancesRepository.save(activeSubstanceEntity);
        return null;
    }
}
