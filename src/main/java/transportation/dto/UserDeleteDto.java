package transportation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDeleteDto {
    private LocalDate deletionDate;
}
