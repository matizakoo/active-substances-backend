package pl.engineer.active.substances.user;

import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.engineer.active.substances.entity.UserEntity;
import pl.engineer.active.substances.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserRegistrationDTO userRegisterationDto) {
        userRepository.saveAndFlush(
                new UserEntity()
                        .setUsername(userRegisterationDto.getUsername())
                        .setPassword(passwordEncoder.encode(userRegisterationDto.getPassword())));
    }

    public UserEntity getUserByName(String name) {
        return userRepository.findByUsername(name).get();
    }
}