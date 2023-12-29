package web.twillice.rest.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements RestExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException e) {
        return buildResponse("Invalid request data format");
    }
}
