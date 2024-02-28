package com.vaaq.school.webSecurity.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vaaq.school.webSecurity.config.JwtService;
import com.vaaq.school.webSecurity.token.Token;
import com.vaaq.school.webSecurity.token.TokenRepository;
import com.vaaq.school.webSecurity.token.TokenType;
import com.vaaq.school.webSecurity.user.Role;
import com.vaaq.school.webSecurity.user.User;
import com.vaaq.school.webSecurity.user.UserRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  // Repositories for accessing user and token data.
  private final UserRepository repository;
  private final TokenRepository tokenRepository;

  // Encoder for encoding passwords.
  private final PasswordEncoder passwordEncoder;

  // Service for handling JSON Web Tokens (JWT).
  private final JwtService jwtService;
  // AuthenticationManager for authenticating users.
  private final AuthenticationManager authenticationManager;

  // Registers an admin and returns the authentication response.
  public AuthenticationResponse adminRegister(RegisterRequest request) {
    // Create a new user with admin role.
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();
    // Save the user to the repository.
    var savedUser = repository.save(user);
    // Generate JWT token and refresh token.
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    // Save the user's token to the token repository.
    saveUserToken(savedUser, jwtToken);
    // Return the authentication response.
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  // Registers a user and returns the authentication response.
  public AuthenticationResponse userRegister(RegisterRequest request) {
    // Create a new user with user role.
    var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    // Save the user to the repository.
    var savedUser = repository.save(user);
    // Generate JWT token and refresh token.
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    // Save the user's token to the token repository.
    saveUserToken(savedUser, jwtToken);
    // Return the authentication response.
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  // Authenticates a user and returns the authentication response.
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // Authenticate the user using Spring Security AuthenticationManager.
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    // Retrieve the authenticated user from the repository.
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    // Generate JWT token and refresh token.
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    // Revoke all previous user tokens.
    revokeAllUserTokens(user);
    // Save the user's new token to the token repository.
    saveUserToken(user, jwtToken);
    // Return the authentication response.
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  // Saves a user's token to the token repository.
  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  // Revokes all valid tokens associated with a user.
  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    // Set all valid tokens to be expired and revoked.
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    // Save the changes to the token repository.
    tokenRepository.saveAll(validUserTokens);
  }

  // Refreshes the authentication token.
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    // Extract the refresh token from the Authorization header.
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      // Retrieve the user associated with the email.
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      // Check if the refresh token is valid.
      if (jwtService.isTokenValid(refreshToken, user)) {
        // Generate a new access token.
        var accessToken = jwtService.generateToken(user);
        // Revoke all previous user tokens.
        revokeAllUserTokens(user);
        // Save the new access token to the token repository.
        saveUserToken(user, accessToken);
        // Create an authentication response with the new access and refresh tokens.
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        // Write the authentication response to the HTTP response output stream.
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }

  // Ändrar en användares roll från användare till administratör.
    @Transactional
    public void promoteToAdmin(String userEmail) {
    // Hämta användaren från repository med e-postadressen.
    User user = repository.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("Användaren hittades inte: " + userEmail));

    // Ändra användarens roll till ADMIN.
    user.setRole(Role.ADMIN);

    // Spara ändringarna i användarrepositoryn.
    repository.save(user);
  }

}
