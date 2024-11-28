package pl.engineer.active.substances.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.entity.CureEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.service.CureService;
import pl.engineer.active.substances.service.DiseaseService;

import java.util.List;

import static pl.engineer.active.substances.controller.advice.Endpoint.DISEASE;
import static pl.engineer.active.substances.controller.advice.Endpoint.DOCTOR;

@RestController
@RequestMapping(value = DOCTOR + DISEASE)
@AllArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;
    private final CureService cureService;
    /**
     * Create @ActiveSubstanceEntity
     * @param diseaseDTO
     * */
    @PostMapping
    public ResponseEntity<InfoDTO> createActiveSubstance(@RequestBody @Valid DiseaseDTO diseaseDTO) {
        diseaseService.addNewDisease(diseaseDTO);
        cureService.saveRelationList(diseaseDTO);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    @GetMapping
    public ResponseEntity<List<DiseaseDTO>> getAllDiseases() {
        return ResponseEntity.ok(diseaseService.findAllDiseases());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<InfoDTO> deleteDisease(@PathVariable(name = "id") Integer id) {
        diseaseService.deleteDisease(id);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }


}
