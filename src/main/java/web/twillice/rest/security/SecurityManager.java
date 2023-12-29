package web.twillice.rest.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import web.twillice.rest.model.User;
import web.twillice.rest.repository.UserRepository;

@ApplicationScoped
public class SecurityManager {
    @Inject
    private UserRepository userRepository;
    @Inject
    private SecurityContext securityContext;
    @Inject
    private JWTManager jwtManager;

    public String createUserToken(User user) throws WebApplicationException {
        try {
            return jwtManager.createToken(user.getUsername(), user.getRole());
        } catch (Exception e) {
            throw new WebApplicationException("Internal server error when creating authentication token", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public User getAuthenticatedUser() throws WebApplicationException {
        // TODO: make it return user id instead of finding User. what's the point of jwt if we access the database every time?
        String username = securityContext.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED);
        return user;
    }
}
