package pl.engineer.active.substances.controller;

import org.apache.tomcat.util.openssl.SSL_set_info_callback$cb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.repository.ActiveSubstancesRepository;

import java.util.List;

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
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    @GetMapping
    public ResponseEntity<InfoDTO> test() {
        System.out.println(activeSubstancesRepository.findActiveSubstancesWithoutConflicts("flu", List.of(1,5,3,2)));
        return null;
    }
}
