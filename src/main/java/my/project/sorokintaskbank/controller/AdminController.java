package my.project.sorokintaskbank.controller;

import lombok.RequiredArgsConstructor;
import my.project.sorokintaskbank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/operation")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/show/all/users")// SHOW_ALL_USERS
    public ResponseEntity<?> viewAllUsers(){
        return ResponseEntity.ok(userService.forViewAllUsers());
    }

}
