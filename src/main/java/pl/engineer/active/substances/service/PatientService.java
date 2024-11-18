package pl.engineer.active.substances.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.PatientDTO;
import pl.engineer.active.substances.dto.PatientDiseaseSubstanceDTO;
import pl.engineer.active.substances.entity.PatientDiseaseSubstanceEntity;
import pl.engineer.active.substances.entity.PatientEntity;
import pl.engineer.active.substances.exception.PatientException;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.mapper.DiseaseMapper;
import pl.engineer.active.substances.mapper.PatientMapper;
import pl.engineer.active.substances.mapper.UserMapper;
import pl.engineer.active.substances.repository.PatientDiseaseRepository;
import pl.engineer.active.substances.repository.PatientRepository;
import pl.engineer.active.substances.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiseaseMapper diseaseMapper;
    @Autowired
    private ActiveSubstancesMapper activeSubstancesMapper;
    @Autowired
    private PatientDiseaseRepository patientDiseaseRepository;
    @Transactional
    public void save(PatientEntity patient) {
        patientRepository.save(patient);
    }

    @Transactional
    public void saveDiseaseForPatient(PatientDiseaseSubstanceEntity patient) {
        patientDiseaseRepository.save(patient);
    }

    @Transactional
    public void deleteDisease(Integer id, Integer id2) {
        patientDiseaseRepository.deleteSpecificDiseaseForPatient(id, id2);
    }

    @Transactional
    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }

    public void addNewPatient(PatientDTO patientDTO, String name) {
        if(patientRepository.findByUniqueId(patientDTO.getUniqueId()).isPresent()) {
            throw new PatientException("Pacjent z tym peslem już istnieje");
        }

        PatientEntity patientEntity = patientMapper.toPatientEntity(patientDTO);
        System.out.println("aa: " + userService.getUserByName(name));
        patientEntity.setUserEntity(userService.getUserByName(name));

        save(patientEntity);
    }

    public List<PatientDTO> getAll() {
        System.out.println(patientRepository.findAll().get(0).getUserEntity().getUsername());
        List<PatientDTO> list = new ArrayList<>();
        for(PatientEntity patient: patientRepository.findAll()) {
            PatientDTO patientDTO = patientMapper.toPatientDTO(patient);
            patientDTO.setUserDTO(userMapper.mapToUserDTO(patient.getUserEntity()));
            list.add(patientDTO);
        }

        return list;
    }

    public void addNewDiseaseForPatient(PatientDiseaseSubstanceDTO patientDiseaseSubstanceDTO) {
        for(ActiveSubstanceDTO activeSubstanceDTO: patientDiseaseSubstanceDTO.getDiseaseActiveSubstancesDTOList().get(0).getActiveSubstanceDTO()) {
            PatientDiseaseSubstanceEntity act = new PatientDiseaseSubstanceEntity(
                    userMapper.mapToUserEntity(patientDiseaseSubstanceDTO.getUserDTO()),
                    patientMapper.toPatientEntity(patientDiseaseSubstanceDTO.getPatientDTO()),
                    diseaseMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(
                            patientDiseaseSubstanceDTO.getDiseaseActiveSubstancesDTOList().get(0).getDiseaseDTO()
                    ),
                    activeSubstancesMapper.mapActiveSubstancesDTOToActiveSubstancesEntity(activeSubstanceDTO)
            );

            saveDiseaseForPatient(act);
        }
    }
}
