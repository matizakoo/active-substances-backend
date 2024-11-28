package pl.engineer.active.substances.controller.advice;

import jakarta.persistence.Table;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.engineer.active.substances.dto.InfoDTO;
import pl.engineer.active.substances.exception.ActiveSubstanceException;
import pl.engineer.active.substances.exception.CategoryException;
import pl.engineer.active.substances.exception.ContractorException;

/**
 * Exception advice controller to catch exceptions by default and return error response
 * */
@ControllerAdvice
public class ExceptionAdviceController {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<InfoDTO> handleContractorException(RuntimeException e) {
        return ResponseHelper.response400(e.getMessage());
    }
}
