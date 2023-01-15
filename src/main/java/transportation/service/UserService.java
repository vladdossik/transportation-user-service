package transportation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import transportation.dto.UserPostDto;
import transportation.dto.UserPutDto;
import transportation.dto.UserResponseDto;
import transportation.mapper.UserMapper;
import transportation.model.User;
import transportation.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto save(UserPostDto userPostDto) {
        User user = User.builder()
                .firstName(userPostDto.getFirstName())
                .lastName(userPostDto.getLastName())
                .patronymic(userPostDto.getPatronymic())
                .passport(userPostDto.getPassport())
                .issueDate(userPostDto.getIssueDate())
                .creationDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .issuePlace(userPostDto.getIssuePlace()).build();
        return userMapper.toResponseDto(userRepository.save(user));
    }

    public List<UserResponseDto> getAll() {
        List<User> list = userRepository.getAllByDeletionDateIsNull();
        return list.stream().map(userMapper::toResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto getById(Long id){
        return userMapper.toResponseDto(userRepository.findByIdAndDeletionDateIsNull(id));
    }

    public String deleteAll() {
        userRepository.deleteAll();
        return "Пользователи удалены";
    }

    @Transactional
    public String delete(Long id) {
        userRepository.delete(id);
        return "Пользователь удален";
    }

    @Transactional
    public UserPutDto update(Long id, UserPutDto userPutDto) {
        userRepository.update(id, userPutDto.getFirstName(), userPutDto.getLastName(), userPutDto.getPatronymic(),
                userPutDto.getPassport(), userPutDto.getIssueDate(), userPutDto.getIssuePlace());
        userRepository.findById(id).get();
        return userPutDto;
    }
}
