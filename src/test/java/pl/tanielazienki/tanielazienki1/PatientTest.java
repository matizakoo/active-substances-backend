package pl.tanielazienki.tanielazienki1;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.engineer.active.substances.ActiveSubstances;
import pl.engineer.active.substances.dto.PatientDTO;
import pl.engineer.active.substances.dto.PatientDiseaseSubstanceDTO;
import pl.engineer.active.substances.exception.PatientException;
import pl.engineer.active.substances.service.PatientService;
import pl.engineer.active.substances.user.UserDTO;
import pl.engineer.active.substances.user.UserRegistrationDTO;
import pl.engineer.active.substances.user.UserService;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(classes = ActiveSubstances.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientTest {
    private final PatientService patientService;
    UserDTO userDTO = new UserDTO(1L, "test", "$2a$12$dFRrg89fT6VlJRkatGlv/eXJZKtHWtE9bsMRfZRGynztXxa4J8FtO", "test", "test");
    PatientDTO patientDTO = new PatientDTO(1, "Mateusz", "Krzak", "56547549485", 21, "M", userDTO);
    PatientDTO subPatientDTO = new PatientDTO(1, "Mateusz", "Krzak", "56547549485", 21, "M", null);
    PatientDTO patientDTO2 = new PatientDTO(2, "Mateusz", "Krzak", "565475494", 21, "M", userDTO);
    PatientDTO patientDTO3 = new PatientDTO(3, "Kornelka", "Latze", "56547549454", -10, "K", userDTO);

    @Autowired
    public PatientTest(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Create new patient
     * OK
     * */
    @Test
    @Order(1)
    public void createNewPatient() {
        patientService.addNewPatient(patientDTO, userDTO.getName());
        assertEquals("Mateusz",patientService.getOnePatientById(String.valueOf(1)).getPatientDTO().getName());
    }

    /**
     * Create new patient with wrong uniqueId (pesel)
     * FAIL
     * */
    @Test
    @Order(2)
    public void createNewPatientWrongUniqueId() {
        PatientException patientException = assertThrows(PatientException.class, () -> patientService.addNewPatient(patientDTO2, userDTO.getName()));
        assertEquals("Błędny pesel pacjenta", patientException.getMessage());
    }

    /**
     * Create new patient who already exists
     * FAIL
     * */
    @Test
    @Order(3)
    public void createExistingPatient() {
        PatientException patientException = assertThrows(PatientException.class, () -> patientService.addNewPatient(patientDTO, userDTO.getName()));
        assertEquals("Pacjent z tym peslem już istnieje", patientException.getMessage());
    }

    /**
     * Create new patient who has less than 0 year
     * FAIL
     * */
    @Test
    @Order(4)
    public void createPatientWhoHasLessThan0years() {
        PatientException patientException = assertThrows(PatientException.class, () -> patientService.addNewPatient(patientDTO3, userDTO.getName()));
        assertEquals("Błędny wiek pacjenta, nie może miec mniej niż 0 lat", patientException.getMessage());
    }

    /**
     * Get all
     * OK
     * */
    @Test
    @Order(5)
    public void returnAllPatients() {
        List<PatientDTO> list = patientService.getAll();
        assertEquals(1, list.size());
    }

    /**
     * Return one patient
     * OK
     * */
    @Test
    @Order(6)
    public void returnOnePatient() {
        PatientDiseaseSubstanceDTO patientDTOS = patientService.getOnePatientById(patientDTO.getId().toString());
        assertEquals(true, patientDTOS.getPatientDTO().equals(subPatientDTO));
    }

    /**
     * Return one patient who does not exist
     * FALSE
     * */
    @Test
    @Order(6)
    public void returnOnePatientWhoDoesNotExist() {
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> patientService.getOnePatientById(patientDTO2.getId().toString()));
        assertEquals("No value present", noSuchElementException.getMessage());
    }


}
