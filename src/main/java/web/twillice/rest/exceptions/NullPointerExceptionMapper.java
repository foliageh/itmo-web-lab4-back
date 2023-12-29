package web.twillice.rest.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NullPointerExceptionMapper implements RestExceptionMapper<NullPointerException> {
    public Response toResponse(NullPointerException e) {
        return buildResponse("Invalid request data format");
    }
}
