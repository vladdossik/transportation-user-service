package transportation.mapper;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;
import transportation.AbstractInitialization;
import users.UserPageResponse;
import users.UserResponseDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserMapperTest extends AbstractInitialization {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void userToResponseDtoTest() {
        UserResponseDto response = userMapper.toResponseDto(user);

        assertEquals(response.getAmountOfOrders(), user.getAmountOfOrders());
        assertEquals(response.getIssuePlace(), user.getIssuePlace());
        assertEquals(LocalDate.parse(response.getIssueDate()), user.getIssueDate());
        assertEquals(response.getFirstName(), user.getFirstName());
        assertEquals(response.getLastName(), user.getLastName());
        assertEquals(response.getPatronymic(), user.getPatronymic());
        assertEquals(response.getCreationDate(), user.getCreationDate());
        assertEquals(response.getLastUpdateDate(), user.getLastUpdateDate());
        assertEquals(response.getPassport(), user.getPassport());
    }

    @Test
    void userPageToPageResponseTest() {
        UserPageResponse response = userMapper.toPageResponseDto(page, "", Sort.Direction.ASC);

        assertEquals(response.getPageNumber(), page.getNumber());
        assertEquals(response.getPageSize(), page.getSize());
        assertEquals(response.getAmountPages(), page.getTotalPages());

        assertNotNull(response.getUsers());
        assertEquals(1, response.getUsers().size());
        assertEquals(response.getUsers().get(0).getAmountOfOrders(), user.getAmountOfOrders());
        assertEquals(response.getUsers().get(0).getIssuePlace(), user.getIssuePlace());
        assertEquals(LocalDate.parse(response.getUsers().get(0).getIssueDate()), user.getIssueDate());
        assertEquals(response.getUsers().get(0).getFirstName(), user.getFirstName());
        assertEquals(response.getUsers().get(0).getLastName(), user.getLastName());
        assertEquals(response.getUsers().get(0).getPatronymic(), user.getPatronymic());
        assertEquals(response.getUsers().get(0).getCreationDate(), user.getCreationDate());
        assertEquals(response.getUsers().get(0).getLastUpdateDate(), user.getLastUpdateDate());
        assertEquals(response.getUsers().get(0).getPassport(), user.getPassport());
    }
}
