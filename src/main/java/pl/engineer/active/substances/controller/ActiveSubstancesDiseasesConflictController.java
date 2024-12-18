package pl.engineer.active.substances.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.openssl.SSL_set_info_callback$cb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.ActiveSubstancesDiseasesConflictDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.service.ActiveSubstancesDiseasesConflictService;
import pl.engineer.active.substances.service.DiseaseService;

import java.util.List;

import static pl.engineer.active.substances.controller.advice.Endpoint.*;

@RestController
@RequestMapping(value = DOCTOR + ACTIVE_SUBSTANCES_DISEASES_CONFLICTS)
@AllArgsConstructor
public class ActiveSubstancesDiseasesConflictController {
    private final ActiveSubstancesDiseasesConflictService activeSubstancesDiseasesConflictService;
    private final DiseaseService diseaseService;

    /**
     * Create conflict with between ActiveSubstanceEntity and DiseaseEntity
     * @param activeSubstancesDiseasesConflictDTO which represents @ActiveSubstanceDTO and list of @DiseaseDTO
     * */
    @PostMapping
    private ResponseEntity<InfoDTO> createConflict(@RequestBody ActiveSubstancesDiseasesConflictDTO activeSubstancesDiseasesConflictDTO) {
        activeSubstancesDiseasesConflictService.createActiveSubstancesDiseasesConflict(activeSubstancesDiseasesConflictDTO);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    /**
     * Returns @ActiveSubstanceDiseaseConflictDTO which represents conflict between @ActiveSubstanceEntity and list of @DiseaseEntity
     * */
    @GetMapping
    private ResponseEntity<List<ActiveSubstancesDiseasesConflictDTO>> getAll() {
        return ResponseEntity.ok(activeSubstancesDiseasesConflictService.getConflictsGroupedBySubstance());
    }

    /**
     * Returns list of @DiseaseDTO without conflict with @ActiveSubstanceEntity between them
     * @param id disease
     * */
    @GetMapping(value = "/activeSubstanceId/{id}")
    private ResponseEntity<List<DiseaseDTO>> getDiseasesNotInConflictWithActiveSubstance(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(diseaseService.getAllDiseasesNotInConflictWithActiveSubstance(id));
    }

    /**
     * Remove conflicting relation between @ActiveSubstanceEntity and @DiseaseEntity
     * @param id id of active substance
     * @param id2 id of disease
     * */
    @DeleteMapping
    private ResponseEntity<InfoDTO> getDiseasesNotInConflictWithActiveSubstance(@RequestParam(name = "id") Integer id,
                                                                                         @RequestParam(name = "id2") Integer id2) {
        activeSubstancesDiseasesConflictService.deleteRelation(id, id2);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }


}
