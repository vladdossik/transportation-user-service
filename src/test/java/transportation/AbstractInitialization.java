package transportation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import transportation.model.User;
import users.UserPageResponse;
import users.UserPostDto;
import users.UserPutDto;
import users.UserResponseDto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public abstract class AbstractInitialization {
    protected static User user;
    protected static UserResponseDto userResponseDto;
    protected static UserPutDto userPutDto;
    protected static UserPostDto userPostDto;
    protected static UserPageResponse userPageResponse;
    protected static Page<User> page;
    protected static UUID userExternalId = UUID.randomUUID();
    protected static ZonedDateTime creationDate = ZonedDateTime.now();
    protected static ZonedDateTime lastUpdateDate = ZonedDateTime.now();

    @BeforeAll
    public static void init() {
        user = new User();
        user.setId(1L);
        user.setExternalId(userExternalId);
        user.setFirstName("Alice");
        user.setLastName("Ramazanova");
        user.setPatronymic("Vladimirovna");
        user.setCreationDate(creationDate);
        user.setLastUpdateDate(lastUpdateDate);
        user.setPassport("1234567890");
        user.setIssueDate(LocalDate.parse("2020-12-12"));
        user.setIssuePlace("Moscow");
        user.setAmountOfOrders(10L);

        userResponseDto = new UserResponseDto();
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setPatronymic(user.getPatronymic());
        userResponseDto.setPassport(user.getPassport());
        userResponseDto.setCreationDate(user.getCreationDate());
        userResponseDto.setLastUpdateDate(user.getLastUpdateDate());
        userResponseDto.setIssueDate(user.getIssueDate().toString());
        userResponseDto.setIssuePlace(user.getIssuePlace());

        userPutDto = new UserPutDto();
        userPutDto.setFirstName(user.getFirstName());
        userPutDto.setLastName(user.getLastName());
        userPutDto.setPassport(user.getPassport());
        userPutDto.setPatronymic(user.getPatronymic());
        userPutDto.setIssueDate(user.getIssueDate().toString());
        userPutDto.setIssuePlace(user.getIssuePlace());

        userPostDto = new UserPostDto();
        userPostDto.setFirstName(user.getFirstName());
        userPostDto.setLastName(user.getLastName());
        userPostDto.setPatronymic(user.getPatronymic());
        userPostDto.setPassport(user.getPassport());
        userPostDto.setIssueDate(user.getIssueDate().toString());
        userPostDto.setIssuePlace(user.getIssuePlace());

        userPageResponse = new UserPageResponse();
        userPageResponse.setUsers(List.of(userResponseDto));
        userPageResponse.setAmountPages(1);
        userPageResponse.setPageNumber(1);
        userPageResponse.setPageSize(1);
        userPageResponse.setSortBy("sort");
        userPageResponse.setDirection(Sort.Direction.ASC.name());

        page = new PageImpl<>(List.of(user),
                PageRequest.of(1, 1, Sort.Direction.ASC, "sort"), 1);
    }
}
