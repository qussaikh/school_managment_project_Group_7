package com.vaaq.school.webSecurity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // Service responsible for user-related business logic.
    private final UserService service;

    // Endpoint for changing the password of the currently authenticated user.
    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        // Delegate the password change request to the service.
        service.changePassword(request, connectedUser);

        // Return a successful response.
        return ResponseEntity.ok().build();
    }


}
