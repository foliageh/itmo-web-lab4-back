package web.twillice.rest.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.twillice.rest.http.requests.UserAuthRequest;
import web.twillice.rest.http.resources.TokenResource;
import web.twillice.rest.model.User;
import web.twillice.rest.repository.UserRepository;
import web.twillice.rest.security.SecurityManager;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    private UserRepository repository;
    @Inject
    private SecurityManager securityManager;

    @POST @Path("/register")
    public TokenResource register(@Valid UserAuthRequest requestData) {
        if (repository.findByUsername(requestData.getUsername()) != null)
            throw new WebApplicationException("User with this username already exists", Response.Status.CONFLICT);

        User user = User.builder()
                .username(requestData.getUsername())
                .password(requestData.getPassword()).build();
        user = repository.save(user);

        String token = securityManager.createUserToken(user);
        return new TokenResource(token);
    }

    @POST @Path("/login")
    public TokenResource login(@Valid UserAuthRequest requestData) {
        User user = repository.findByUsername(requestData.getUsername());
        if (user == null || !user.checkPassword(requestData.getPassword()))
            throw new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED);

        String token = securityManager.createUserToken(user);
        return new TokenResource(token);
    }
}
