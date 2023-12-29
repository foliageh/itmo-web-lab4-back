package web.twillice.rest.http.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthRequest {
    @NotNull(message = "Username must not be null")
    @Size(min = 6, max = 20, message = "Username must be between 6 and 20 characters")
    @Pattern(regexp = "[a-zA-Z0-9_]+", message = "Username must contain only letters, numbers and underscores")
    String username;
    @NotNull(message = "Password must not be null")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    String password;
}
