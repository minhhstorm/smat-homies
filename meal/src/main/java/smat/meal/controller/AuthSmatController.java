package smat.meal.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smat.meal.dto.LoginRequestDTO;
import smat.meal.dto.MessageResponse;
import smat.meal.dto.RegisterRequestDTO;
import smat.meal.entity.UserEntity;
import smat.meal.repository.UserRepository;
import smat.meal.service.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/smat")
@AllArgsConstructor
public class AuthSmatController {

    private final AuthService authService;
    private Optional<UserEntity> userEntity;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;




    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Tên đăng nhập đã tồn tại!!"));
        }
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email đăng kí đã tồn tại!!"));
        }

        authService.signup(registerRequestDTO);
        return new ResponseEntity<>("Đăng kí tài khoản thành công", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Kích hoạt tài khoản thành công", HttpStatus.OK);
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequestDTO loginRequestDTO) {
        if (loginRequestDTO.getUsername().isEmpty() && loginRequestDTO.getPassword().isEmpty()) {
            return  ResponseEntity.badRequest().body(new MessageResponse("Error: Hãy nhập username và password"));
        }
        if (loginRequestDTO.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Hãy nhập username!!"));
        }
        if (loginRequestDTO.getPassword().isEmpty()) {
            return  ResponseEntity.badRequest().body(new MessageResponse("Error: Hãy nhập password!!"));
        }
        userEntity = userRepository.findByUsername(loginRequestDTO.getUsername());
        if (userRepository.existsByUsername(loginRequestDTO.getUsername())) {
            boolean checkPass = passwordEncoder.matches(loginRequestDTO.getPassword(),userEntity.get().getPassword());
            if (!checkPass) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Tên đăng nhập hoặc mật khẩu không đúng1!!"));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Tên đăng nhập hoặc mật khẩu không đúng!!"));
        }
        if(!userEntity.get().isEnabled()) {
            System.out.println(userEntity.get().isEnabled());
            return  ResponseEntity.badRequest().body(new MessageResponse("Error: Tài khoản của bạn chưa được kích hoạt!! Liên hệ với admin"));
        }
        return authService.login(loginRequestDTO);
    }
}
