package transportation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
public class UserPutDto {
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Отчество")
    private String patronymic;
    @Schema(description = "Паспорт")
    private String passport;
    @Schema(description = "Дата выдачи паспорта")
    private LocalDate issueDate;
    @Schema(description = "Где выдан")
    private String issuePlace;

}
