package miu.edu.onlineRetailSystem.auth;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.onlineRetailSystem.nonDomain.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class RegisterRequest {

  @NotNull
  @NotEmpty
  private String firstname;
  private String lastname;
  @NotNull
  @NotEmpty
  @Email
  private String email;
  @NotNull
  @NotEmpty
  private String password;
  @NotNull
  @NotEmpty
  private Role role;
}
