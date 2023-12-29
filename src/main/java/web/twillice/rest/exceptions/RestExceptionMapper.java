package web.twillice.rest.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.List;
import java.util.Map;

public interface RestExceptionMapper<E extends Throwable> extends ExceptionMapper<E> {
    default Response buildResponse(Object errorInfo) {
        return buildResponse(Response.Status.BAD_REQUEST, errorInfo);
    }

    default Response buildResponse(int status, Object errorInfo) {
        return buildResponse(Response.Status.fromStatusCode(status), errorInfo);
    }

    default Response buildResponse(Response.Status status, Object errorInfo) {
        return Response.status(status).
                entity(Map.of("errors", errorInfo instanceof List ? errorInfo : List.of(errorInfo))).
                type(MediaType.APPLICATION_JSON).build();
    }
}
