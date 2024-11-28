package pl.engineer.active.substances.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.dto.*;
import pl.engineer.active.substances.entity.PatientDiseaseSubstanceEntity;
import pl.engineer.active.substances.entity.PatientEntity;
import pl.engineer.active.substances.entity.UserEntity;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.mapper.DiseaseMapper;
import pl.engineer.active.substances.mapper.PatientMapper;
import pl.engineer.active.substances.mapper.UserMapper;
import pl.engineer.active.substances.repository.PatientDiseaseRepository;
import pl.engineer.active.substances.repository.PatientRepository;
import pl.engineer.active.substances.repository.UserRepository;
import pl.engineer.active.substances.service.DiseaseService;
import pl.engineer.active.substances.service.PatientService;

import java.util.*;

import static pl.engineer.active.substances.controller.advice.Endpoint.DOCTOR;
import static pl.engineer.active.substances.controller.advice.Endpoint.PATIENT;

@RestController
@RequestMapping(value = DOCTOR + PATIENT)
public class PatientRepositoryController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private DiseaseMapper diseaseMapper;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private PatientDiseaseRepository patientDiseaseRepository;

    /**
     * Create new @PatientEntity
     * @param patientEntity DTO of new patient
     * @param doctorName string of doctorName
     * */
    @PostMapping
    public ResponseEntity<InfoDTO> addNewPatient(@RequestBody PatientDTO patientEntity, @RequestParam(name = "principal") String doctorName) {
        System.out.println(doctorName);
        patientService.addNewPatient(patientEntity, doctorName);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    /**
     * Create new disease for patient
     * @param patientDiseaseSubstanceDTO
     * */
    @PostMapping(value = "/addNewDiseaseForPatient")
    public ResponseEntity<InfoDTO> addNewPatient(@RequestBody PatientDiseaseSubstanceDTO patientDiseaseSubstanceDTO) {
        patientService.addNewDiseaseForPatient(patientDiseaseSubstanceDTO);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    /**
     * Delete disease for patient
     * @param id patient
     * @param id2 disease
     * */
    @DeleteMapping(value = "/deleteDiseaseForPatient")
    public ResponseEntity<InfoDTO> deleteDiseaseForPatient(@RequestParam(name = "id") Integer id,
                                                           @RequestParam(name = "id2") Integer id2) {
        patientService.deleteDisease(id, id2);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }

    /**
     * Delete patient
     * @param id patient
     * */
    @DeleteMapping(value = "/deletePatient")
    public ResponseEntity<InfoDTO> deleteDiseaseForPatient(@RequestParam(name = "idPatient") Integer id) {
        System.out.println(id);
        patientService.deletePatient(id);
        return ResponseEntity.ok(new InfoDTO("ok"));
    }


    /**
     * Returns list of @PatientDTO
     * */
    @GetMapping(value = "/all")
    public ResponseEntity<List<PatientDTO>> getAll() {
        return ResponseEntity.ok(patientService.getAll());
    }

    /**
     * Returns one patient by id
     * @param idPatient patient
     * */
    @GetMapping(value = "/getOne")
    public ResponseEntity<PatientDiseaseSubstanceDTO> get(@RequestParam(value = "idPatient") String idPatient) {
        PatientEntity patientEntity = patientRepository.findById(Integer.valueOf(idPatient)).get();
        PatientDTO patientDTO = patientMapper.toPatientDTO(patientEntity);
        Map<DiseaseDTO, List<ActiveSubstanceDTO>> map = new LinkedHashMap<>();
        for(PatientDiseaseSubstanceEntity entity: patientEntity.getPatientDiseaseSubstances()) {
            DiseaseDTO diseaseDTO = diseaseMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(entity.getDisease());
            ActiveSubstanceDTO activeSubstanceDTO = activeSubstancesMapper.mapActiveSubstancesEntityToActiveSubstancesDTO(entity.getActiveSubstance());
            if (!map.containsKey(diseaseDTO)) {
                map.put(diseaseDTO, new ArrayList<>(List.of(activeSubstanceDTO)));
            } else {
                map.get(diseaseDTO).add(activeSubstanceDTO);
            }
        }
        PatientDiseaseSubstanceDTO patientDiseaseSubstanceDTO = null;
        List<DiseaseActiveSubstancesDTO> diseaseActiveSubstancesDTO = new ArrayList<>();

        for (Map.Entry<DiseaseDTO, List<ActiveSubstanceDTO>> entry : map.entrySet()) {
            DiseaseDTO diseaseDTO = entry.getKey();
            List<ActiveSubstanceDTO> activeSubstances = entry.getValue();

            diseaseActiveSubstancesDTO.add(
                    new DiseaseActiveSubstancesDTO(diseaseDTO, activeSubstances)
            );
        }
        UserEntity userEntity = patientRepository.findUserByPatientId(Integer.valueOf(idPatient));

        patientDiseaseSubstanceDTO = new PatientDiseaseSubstanceDTO(
                userMapper.mapToUserDTO(patientEntity.getUserEntity()),
                patientDTO,
                diseaseActiveSubstancesDTO);

        return ResponseEntity.ok(patientDiseaseSubstanceDTO);
    }

    /**
     * Returns list of @DiseaseDTO for patient
     * @param id patient
     * */
    @GetMapping(value = "/getDiseasesForPatient")
    public ResponseEntity<List<DiseaseDTO>> getDiseasesForPatient(@RequestParam(name = "idPatient") Integer id) {
        return ResponseEntity.ok(diseaseService.getDiseasesNotExistingForPatient(id));
    }
}
