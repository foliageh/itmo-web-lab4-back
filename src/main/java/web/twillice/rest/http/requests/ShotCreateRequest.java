package web.twillice.rest.http.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShotCreateRequest {
    @NotNull(message = "X must not be null")
    @Digits(integer = 1, fraction = 15, message = "X must be decimal with a maximum of 15 fractional digits and 1 integral digit")
    Double x;
    @NotNull(message = "Y must not be null")
    @Digits(integer = 1, fraction = 15, message = "Y must be decimal with a maximum of 15 fractional digits and 1 integral digit")
    Double y;
    @NotNull(message = "R must not be null")
    @Digits(integer = 1, fraction = 15, message = "R must be decimal with a maximum of 15 fractional digits and 1 integral digit")
    Double r;
}
