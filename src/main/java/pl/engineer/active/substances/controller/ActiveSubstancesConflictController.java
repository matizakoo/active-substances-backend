package pl.engineer.active.substances.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.ActiveSubstanceConflictDTO;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.ActiveSubstancesConflictEntity;
import pl.engineer.active.substances.repository.ActiveSubstancesRepository;
import pl.engineer.active.substances.service.ActiveSubstancesConflictService;
import pl.engineer.active.substances.service.ActiveSubstancesService;

import javax.sound.midi.SoundbankResource;

import java.util.List;

import static pl.engineer.active.substances.controller.advice.Endpoint.*;

@RestController
@RequestMapping(value = DOCTOR + ACTIVE_SUBSTANCES_CONFLICTS)
@AllArgsConstructor
public class ActiveSubstancesConflictController {
    private final ActiveSubstancesConflictService activeSubstancesConflictService;
    /**
     * Retruns list of @ActiveSubstanceConflictDTO which represents all conflicting relations with @ActiveSubstanceEntity
     * */
    @GetMapping
    public ResponseEntity<List<ActiveSubstanceConflictDTO>> test() {
        return ResponseEntity.ok(activeSubstancesConflictService.findAll());
    }

    /**
     * Create conflict between @ActiveSubstanceEntity
     * */
    @PostMapping
    public ResponseEntity<InfoDTO> createConflict(@RequestBody ActiveSubstanceConflictDTO activeSubstanceConflictDTO) {
        String info = activeSubstancesConflictService.createConflict(activeSubstanceConflictDTO);
        return ResponseEntity.ok(new InfoDTO(info));
    }

    /**
     * Remove conflict between @ActiveSubstnaceEntity
     * */
    @DeleteMapping
    public ResponseEntity<InfoDTO> deleteConflict(@RequestParam(name = "id") Integer id, @RequestParam(name = "id2") Integer id2) {
        activeSubstancesConflictService.deleteConflict(id, id2);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

}
