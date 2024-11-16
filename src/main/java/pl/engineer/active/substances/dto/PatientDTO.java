package pl.engineer.active.substances.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.engineer.active.substances.user.UserDTO;

@AllArgsConstructor
@Data
public class PatientDTO {
    private Integer id;
    private String name;
    private String surname;
    // pesel
    private String uniqueId;
    private Integer age;
    private String gender;
    private UserDTO userDTO;
}
