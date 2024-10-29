package pl.engineer.active.substances.controller;

import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.CureEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.repository.DiseaseRepository;

import static pl.engineer.active.substances.controller.advice.Endpoint.DISEASE;
import static pl.engineer.active.substances.controller.advice.Endpoint.DOCTOR;

@RestController
@RequestMapping(value = DOCTOR + DISEASE)
public class DiseaseController {
    @Autowired
    private DiseaseRepository diseaseRepository;
    @PostMapping
    public ResponseEntity<InfoDTO> hi(@RequestBody DiseaseEntity diseaseEntity){
        diseaseRepository.save(diseaseEntity);
        return null;
    }

    @GetMapping
    public ResponseEntity<InfoDTO> aa() {
        for(DiseaseEntity entity: diseaseRepository.findAll()) {
            System.out.println(entity.toString());
            for(CureEntity entity1: entity.getActiveSubstances()) {
                System.out.println("x: " + entity1.getDiseaseEntity());
            }
        }
        return ResponseEntity.ok(new InfoDTO("kk"));
    }
}
