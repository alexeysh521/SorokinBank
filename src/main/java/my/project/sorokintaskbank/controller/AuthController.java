package my.project.sorokintaskbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.project.sorokintaskbank.dto.AuthRequest;
import my.project.sorokintaskbank.dto.RegisterRequest;
import my.project.sorokintaskbank.model.User;
import my.project.sorokintaskbank.service.AccountService;
import my.project.sorokintaskbank.service.JwtServiceImpl;
import my.project.sorokintaskbank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest request) {// Enter in account

        if(!userService.existsByLogin(request.login()))
            return ResponseEntity.badRequest().body("User not found.");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.login());
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok("Successful account login\n" + token);
    }


    @PostMapping("/register")// user_create
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {// Registration

        if(userService.existsByLogin(request.login()))
            return ResponseEntity.badRequest().body("A user with this name already exists.");

        User user = new User();
        user.setLogin(request.login());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());

        userService.save(user);

        accountService.createDefaultAccount(user);

        UserDetails userd = userDetailsService.loadUserByUsername(request.login());
        String token = jwtService.generateToken(userd);
        return ResponseEntity.ok("Successful registration\n" + token);
    }

}
