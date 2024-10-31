package pl.engineer.active.substances.controller;

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
    public ResponseEntity<InfoDTO> createActiveSubstance(@RequestBody @Valid ActiveSubstanceEntity activeSubstanceEntity) {
        activeSubstancesService.addNewActiveSubstance(activeSubstanceEntity);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    @GetMapping
    public ResponseEntity<List<ActiveSubstanceDTO>> getAllActiveSubstances() {
        return ResponseEntity.ok(activeSubstancesService.getAllActiveSubstances());
    }
}
