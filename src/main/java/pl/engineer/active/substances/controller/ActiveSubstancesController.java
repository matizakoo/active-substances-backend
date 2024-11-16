package pl.engineer.active.substances.controller;

import freemarker.core._ArrayEnumeration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.openssl.SSL_set_info_callback$cb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.repository.ActiveSubstancesRepository;
import pl.engineer.active.substances.service.ActiveSubstancesService;

import java.util.List;

import static pl.engineer.active.substances.controller.advice.Endpoint.*;

@RestController
@RequestMapping(value = DOCTOR + ACTIVE_SUBSTANCES)
@AllArgsConstructor
public class ActiveSubstancesController {
    private final ActiveSubstancesRepository activeSubstancesRepository;
    private final ActiveSubstancesService activeSubstancesService;
    @PostMapping
    public ResponseEntity<InfoDTO> createActiveSubstance(@RequestBody @Valid ActiveSubstanceDTO activeSubstanceDTO) {
        activeSubstancesService.addNewActiveSubstance(activeSubstanceDTO);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    @GetMapping
    public ResponseEntity<List<ActiveSubstanceDTO>> getAllActiveSubstances() {
        return ResponseEntity.ok(activeSubstancesService.getAllActiveSubstances());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InfoDTO> deleteActiveSubstance(@PathVariable(name = "id") Integer id) {
        activeSubstancesService.deleteActiveSubstance(id);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<ActiveSubstanceDTO>> getActiveSubstanceWithoutConflict(@PathVariable(name = "id") Integer diseaseId) {
        return null;
    }
}
