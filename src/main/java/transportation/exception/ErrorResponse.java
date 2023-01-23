package transportation.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class ErrorResponse {
    private  String message;
    private LocalDate timeStamp;
    private String details;

    public ErrorResponse(LocalDate date, String validationFailed, String details) {
        timeStamp = date;
        message = validationFailed;
        this.details = details;
    }
}
