package smat.meal.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smat.meal.dto.JwtLoginResponseDTO;
import smat.meal.dto.LoginRequestDTO;
import smat.meal.dto.MessageResponse;
import smat.meal.dto.RegisterRequestDTO;
import smat.meal.repository.RoleRepository;
import smat.meal.repository.UserRepository;
import smat.meal.service.AuthService;

@RestController
@RequestMapping("/smat")
@AllArgsConstructor
public class AuthSmatController {

    private final AuthService authService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository repository;

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
        authService.verifyAccout(token);
        return new ResponseEntity<>("Kích hoạt tài khoản thành công", HttpStatus.OK);
    }

    @PostMapping("/login")
    public JwtLoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }
}
