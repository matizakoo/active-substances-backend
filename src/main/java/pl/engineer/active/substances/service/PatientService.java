package pl.engineer.active.substances.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.dto.*;
import pl.engineer.active.substances.entity.PatientDiseaseSubstanceEntity;
import pl.engineer.active.substances.entity.PatientEntity;
import pl.engineer.active.substances.entity.UserEntity;
import pl.engineer.active.substances.exception.PatientException;
import pl.engineer.active.substances.mapper.ActiveSubstancesMapper;
import pl.engineer.active.substances.mapper.DiseaseMapper;
import pl.engineer.active.substances.mapper.PatientMapper;
import pl.engineer.active.substances.mapper.UserMapper;
import pl.engineer.active.substances.repository.PatientDiseaseRepository;
import pl.engineer.active.substances.repository.PatientRepository;
import pl.engineer.active.substances.user.UserService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println("zapisano pacjenta " + patient.getSurname());
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
        patientValidation(patientDTO);
        PatientEntity patientEntity = patientMapper.toPatientEntity(patientDTO);
        patientEntity.setUserEntity(userService.getUserByName(name));

        save(patientEntity);
    }

    public void patientValidation(PatientDTO patientDTO) {
        if(patientDTO.getUniqueId().length() != 11) {
            throw new PatientException("Błędny pesel pacjenta");
        }
        if(patientRepository.findByUniqueId(patientDTO.getUniqueId()).isPresent()) {
            throw new PatientException("Pacjent z tym peslem już istnieje");
        }
        if(patientDTO.getAge() < 0) {
            throw new PatientException("Błędny wiek pacjenta, nie może miec mniej niż 0 lat");
        }

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

    public List<PatientDTO> getAllForDoctor(Integer id) {
        List<PatientDTO> list = new ArrayList<>();
        for(PatientEntity patient: patientRepository.findByUserEntityId(id)) {
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

    public PatientDiseaseSubstanceDTO getOnePatientById(String idPatient) {
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
        return patientDiseaseSubstanceDTO;
    }
}
