package com.vaaq.school.webSecurity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

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

    @PutMapping("/update/{userEmail}")
    public ResponseEntity<?> updateUser(
            @PathVariable String userEmail,
            @RequestBody User request
    ) {
        try {
            User updatedUser = service.updateUser(userEmail, request);
            return ResponseEntity.ok("User updated");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build(); // Användaren finns inte
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Något gick fel"); // Generiskt fel
        }
    }

    @DeleteMapping("/delete/{userEmail}")
    public ResponseEntity<String> deleteUser(@PathVariable String userEmail) {
        service.deleteUser(userEmail);
        return ResponseEntity.ok("Användaren har tagits bort.");
    }


    // Endpoint för att ändra en användares roll till administratör.
    @PostMapping("/promoteToAdmin")
    public ResponseEntity<String> promoteToAdmin(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-postadressen är obligatorisk.");
        }
        service.promoteToAdmin(email);
        return ResponseEntity.status(HttpStatus.OK).body("Användarens roll har uppdaterats till administratör.");
    }



}
