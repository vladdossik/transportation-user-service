package transportation.mapper;

import org.springframework.stereotype.Component;
import transportation.model.User;
import users.UserResponseDto;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setPatronymic(user.getPatronymic());
        userResponseDto.setCreationDate(user.getCreationDate());
        userResponseDto.setLastUpdateDate(user.getLastUpdateDate());
        userResponseDto.setPassport(user.getPassport());
        userResponseDto.setIssueDate(user.getIssueDate());
        userResponseDto.setIssuePlace(user.getIssuePlace());
        userResponseDto.setAmountOfOrders(user.getAmountOfOrders());
        return userResponseDto;
    }
}
