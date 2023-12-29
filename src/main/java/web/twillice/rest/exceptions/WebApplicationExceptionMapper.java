package web.twillice.rest.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements RestExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        return buildResponse(e.getResponse().getStatus(), e.getMessage());
    }
}
