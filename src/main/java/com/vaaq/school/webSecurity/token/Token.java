package com.vaaq.school.webSecurity.token;



import com.vaaq.school.webSecurity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

  @Id
  @GeneratedValue
  public Integer id;

  // The actual token string, marked as unique in the database.
  @Column(unique = true)
  public String token;

  // Type of the token (BEARER).
  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  // Many-to-One relationship with the User entity, representing the user associated with the token.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;
}
