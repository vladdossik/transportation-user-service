package transportation.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
public class UserPutDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String passport;
    private LocalDate issueDate; // дата выдачи паспорта
    private String issuePlace; // где выдан
    private Long amountOfOrders; //количество заказов
}
