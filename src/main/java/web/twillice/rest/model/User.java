package web.twillice.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import web.twillice.rest.security.PasswordEncrypter;

@Entity @Table(name = "User_")
@Getter @Setter(value = AccessLevel.PACKAGE)
@Builder @NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PACKAGE)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotNull @Pattern(regexp = "[a-zA-Z0-9_]+")
    private String username;
    @Column(nullable = false)
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER; // TODO: Role[]

    public static class UserBuilder {
        public UserBuilder password(String password) {
            this.password = _encodePassword(password);
            return this;
        }
    }

    void setPassword(String password) {
        this.password = _encodePassword(password);
    }

    private static String _encodePassword(String password) {
        return PasswordEncrypter.encrypt(password);
    }

    public boolean checkPassword(String password) {
        return PasswordEncrypter.checkPassword(password, this.password);
    }
}
