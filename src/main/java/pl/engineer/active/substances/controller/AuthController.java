package pl.engineer.active.substances.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.engineer.active.substances.user.UserRegistrationDTO;
import pl.engineer.active.substances.user.UserService;



@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Controller to log into system
     * */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserRegistrationDTO userRegisterationDto) {
        userService.register(userRegisterationDto);
        return ResponseEntity.ok().build();
    }
}