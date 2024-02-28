package com.vaaq.school.webSecurity.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  // Service responsible for handling authentication logic.
  private final AuthenticationService service;

  @GetMapping
  public String showRegistrationForm(){
    return "registration";
  }

  // Endpoint for admin registration.
  @PostMapping("/adminRegister")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    // Delegate the registration request to the AuthenticationService and return the response.
    return ResponseEntity.ok(service.adminRegister(request));
  }

  // Endpoint for user registration.
  @PostMapping("/userRegister")
  public ResponseEntity<AuthenticationResponse> userRegister(
          @RequestBody RegisterRequest request
  ) {
    // Delegate the user registration request to the AuthenticationService and return the response.
    return ResponseEntity.ok(service.userRegister(request));
  }

  // Endpoint for user authentication.
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    // Delegate the authentication request to the AuthenticationService and return the response.
    return ResponseEntity.ok(service.authenticate(request));
  }

  // Endpoint för att ändra en användares roll till administratör.
  @PostMapping("/promote-to-admin")
  public ResponseEntity<String> promoteToAdmin(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    if (email == null || email.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-postadressen är obligatorisk.");
    }
    service.promoteToAdmin(email);
    return ResponseEntity.status(HttpStatus.OK).body("Användarens roll har uppdaterats till administratör.");
  }

  // Endpoint for refreshing authentication tokens.
  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    // Delegate the refresh token request to the AuthenticationService.
    service.refreshToken(request, response);
  }



}
