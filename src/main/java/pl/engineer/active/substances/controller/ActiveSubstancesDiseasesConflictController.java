package pl.engineer.active.substances.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.ActiveSubstanceConflictDTO;
import pl.engineer.active.substances.dto.ActiveSubstancesDiseasesConflictDTO;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.entity.ActiveSubstancesDiseasesConflictEntity;
import pl.engineer.active.substances.service.ActiveSubstancesDiseasesConflictService;

import java.util.List;

import static pl.engineer.active.substances.controller.advice.Endpoint.*;

@RestController
@RequestMapping(value = DOCTOR + ACTIVE_SUBSTANCES_DISEASES_CONFLICTS)
@AllArgsConstructor
public class ActiveSubstancesDiseasesConflictController {
    private final ActiveSubstancesDiseasesConflictService activeSubstancesDiseasesConflictService;
    @PostMapping
    private ResponseEntity<InfoDTO> createConflict(@RequestBody ActiveSubstancesDiseasesConflictDTO activeSubstancesDiseasesConflictDTO) {
        activeSubstancesDiseasesConflictService.createActiveSubstancesDiseasesConflict(activeSubstancesDiseasesConflictDTO);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    @GetMapping
    private ResponseEntity<List<ActiveSubstancesDiseasesConflictDTO>> getAll() {
        System.out.println(activeSubstancesDiseasesConflictService.getConflictsGroupedBySubstance());
        return ResponseEntity.ok(activeSubstancesDiseasesConflictService.getConflictsGroupedBySubstance());
    }
}
