package com.vaaq.school.webSecurity.user;

import com.vaaq.school.webSecurity.token.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public User updateUser(String userEmail, User request) {
        // Hämta användaren från repository med e-postadressen.
        User user = repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Användaren hittades inte: " + userEmail));

        // Uppdatera användarens information med den nya informationen från förfrågan.
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());

        // Kontrollera om lösenordet ska uppdateras och kryptera det nya lösenordet.
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        // Spara ändringarna i användarrepositoryn och returnera den uppdaterade användaren.
        return repository.save(user);
    }

    @Transactional
    public void deleteUser(String userEmail) {
        // Hämta användaren från repository med e-postadressen.
        User user = repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Användaren hittades inte: " + userEmail));

        // Ta bort alla relaterade rader i token-tabellen för den användaren.
        tokenRepository.deleteByUser(user);

        // Ta bort användaren från repositoryn.
        repository.delete(user);
    }

    // Ändrar en användares roll från användare till administratör.
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
