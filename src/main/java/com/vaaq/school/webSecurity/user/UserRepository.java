package com.vaaq.school.webSecurity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  // Custom query to find a user by their email address.
  Optional<User> findByEmail(String email);

}
