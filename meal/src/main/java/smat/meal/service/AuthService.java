package smat.meal.service;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smat.meal.common.Constant;
import smat.meal.common.ERole;
import smat.meal.dto.JwtLoginResponseDTO;
import smat.meal.dto.LoginRequestDTO;
import smat.meal.dto.RegisterRequestDTO;
import smat.meal.entity.NotificationEmail;
import smat.meal.entity.RoleEntity;
import smat.meal.entity.TokenEntity;
import smat.meal.entity.UserEntity;
import smat.meal.exception.SmatException;
import smat.meal.repository.RoleRepository;
import smat.meal.repository.TokenRepository;
import smat.meal.repository.UserRepository;
import smat.meal.security.JwtProvider;

import java.util.*;
import java.util.stream.Collectors;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void signup(RegisterRequestDTO registerRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequest.getUsername());
        userEntity.setEmail(registerRequest.getEmail());
        userEntity.setPassword(encodePassword(registerRequest.getPassword()));
        userEntity.setCreated(now());
        userEntity.setEnabled(false);
        userEntity.setAddress(registerRequest.getAddress());
        userEntity.setBirthday(registerRequest.getBirthday());
        userEntity.setName(registerRequest.getName());


        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        roles.add(userRole);
        userEntity.setRoles(roles);

        userRepository.save(userEntity);
        String token = generateTokenUser(userEntity);
        String message = mailContentBuilder.build("Cảm ơn bạn đã đăng kí tài khoản Smat Meal, hãy nhấn vào link bên dưới để kích hoạt tài khoản của bạn: \n" +
                Constant.ACTIVATION_EMAIL + "/" + token);
        mailService.sendMailSignUp(new NotificationEmail("Hãy kích hoạt tài khoản của bạn", userEntity.getEmail(), message));
    }

    private String generateTokenUser(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        TokenEntity tokenEntity = new TokenEntity();
        Calendar calendar = Calendar.getInstance();
        tokenEntity.setToken(token);
        tokenEntity.setUserEntity(userEntity);
        tokenEntity.setExpiryDate(calendar.getTime());
        tokenRepository.save(tokenEntity);
        return token;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccout(String token) {
        Optional<TokenEntity> tokenOptional = tokenRepository.findByToken(token);
        tokenOptional.orElseThrow(() -> new SmatException("Mã không hợp lệ"));
        fetchUserAndEnable(tokenOptional.get());
    }

    @Transactional
    void fetchUserAndEnable(TokenEntity tokenEntity) {
        String username = tokenEntity.getUserEntity().getUsername();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(()
                -> new SmatException("Không tìm thấy user có id - " + username));
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    @Transactional
    public JwtLoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authenticationToken = jwtProvider.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList());
        return new JwtLoginResponseDTO(authenticationToken, userDetails.getId()
                                        , userDetails.getUsername()
                                            , userDetails.getEmail()
                                            , roles);
    }

    @Transactional(readOnly = true)
    public UserEntity getCurrentUser() {
//        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found - " + principal.getUsername()));
    }
}
