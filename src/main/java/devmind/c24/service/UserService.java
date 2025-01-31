package devmind.c24.service;

import devmind.c24.dtos.LoginRequestDTO;
import devmind.c24.dtos.LoginResponseDTO;
import devmind.c24.dtos.RegisterRequestDTO;
import devmind.c24.model.User;
import devmind.c24.repository.UserRepository;
import devmind.c24.security.Role;
import devmind.c24.security.UserDetailsImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTService jwtHelper;

    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtHelper.generateJwtCookie(userDetails);

        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new LoginResponseDTO(userDetails.getEmail(), jwtCookie.getValue(), userDetails.getId(), role));
    }

    @Transactional
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email used");
        }

        String role = registerRequestDTO.getRole();
        // TODO: add more sanity checks (email, password, etc.)

        if (role == null || !(role.toUpperCase(Locale.ROOT).equals("USER") || role.toUpperCase(Locale.ROOT).equals("ADMIN"))) {
            throw new RuntimeException("Invalid role");
        }


        User user = new User(null, registerRequestDTO.getEmail(), encoder.encode(registerRequestDTO.getPassword()), Role.valueOf(role));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}