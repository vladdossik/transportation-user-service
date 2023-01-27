package transportation.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import transportation.model.User;
import users.UserPageResponse;
import users.UserResponseDto;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setExternalId(user.getExternalId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setPatronymic(user.getPatronymic());
        userResponseDto.setCreationDate(user.getCreationDate());
        userResponseDto.setLastUpdateDate(user.getLastUpdateDate());
        userResponseDto.setPassport(user.getPassport());
        userResponseDto.setIssueDate(user.getIssueDate().toString());
        userResponseDto.setIssuePlace(user.getIssuePlace());
        userResponseDto.setAmountOfOrders(user.getAmountOfOrders());
        return userResponseDto;
    }

    public UserPageResponse toPageResponseDto(Page<User> page, String sortBy, Sort.Direction direction) {
        UserPageResponse pageResponse = new UserPageResponse();
        pageResponse.setUsers(page.getContent().stream().map(this::toResponseDto).collect(Collectors.toList()));
        pageResponse.setAmountPages(page.getTotalPages());
        pageResponse.setPageNumber(page.getNumber());
        pageResponse.setPageSize(page.getSize());
        pageResponse.setSortBy(sortBy);
        pageResponse.setDirection(direction.name());
        return pageResponse;
    }
}
