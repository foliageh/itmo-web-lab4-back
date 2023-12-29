package web.twillice.rest.http.resources;

import lombok.Value;
import web.twillice.rest.model.Role;

@Value
public class UserResource {
    Long id;
    String username;
    Role role;
}
