package transportation.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private ZonedDateTime creationDate;
    private ZonedDateTime lastUpdateDate;
    private String passport;
    private LocalDate issueDate;
    private String issuePlace;
    private Long amountOfOrders;
    private LocalDate deletionDate;
}
