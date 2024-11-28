package pl.engineer.active.substances.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.engineer.active.substances.dto.InfoDTO;

/**
 * Reponse helper catches exceptions for @ExceptionAdviceController
 * */
public class ResponseHelper {
    public static ResponseEntity<InfoDTO> response400(String e){
        return new ResponseEntity<>(new InfoDTO(e), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<InfoDTO> response200(String e) {
        return new ResponseEntity<>(new InfoDTO(e), HttpStatus.OK);
    }
}
