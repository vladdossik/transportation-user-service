package transportation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID externalId;
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, message = "First name too short")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, message = "Last name too short")
    private String lastName;
    @Size(min = 2, message = "Patronymic name too short")
    private String patronymic;
    private ZonedDateTime creationDate;
    private ZonedDateTime lastUpdateDate;
    @NotBlank(message = "Passport is mandatory")
    @Digits(integer = 10, fraction = 0, message = "Passport entered incorrectly")
    @Size(min = 10, message = "Passport entered incorrectly")
    private String passport;
    @PastOrPresent(message = "IssueDate entered incorrectly")
    private LocalDate issueDate;
    @NotBlank(message = "Issue place is mandatory")
    private String issuePlace;
    private Long amountOfOrders;
    private ZonedDateTime deletionDate;
}
