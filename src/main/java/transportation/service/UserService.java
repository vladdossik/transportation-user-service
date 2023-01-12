package transportation.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transportation.dto.UserPostDto;
import transportation.dto.UserPutDto;
import transportation.dto.UserResponseDto;
import transportation.mapper.UserMapper;
import transportation.model.User;
import transportation.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto saveUser(UserPostDto userPostDto) {
        User user = User.builder()
                .firstName(userPostDto.getFirstName())
                .lastName(userPostDto.getLastName())
                .patronymic(userPostDto.getPatronymic())
                .passport(userPostDto.getPassport())
                .issueDate(userPostDto.getIssueDate())
               .issuePlace(userPostDto.getIssuePlace()).build();
        return userMapper.toResponseDto(userRepository.save(user));
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream().map(userMapper::toResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto getById(Long id) {
        return userMapper.toResponseDto(userRepository.getById(id));
    }

    public String deleteAll() {
        List<User> list = userRepository.findAll();
        userRepository.deleteAll(list);
        return "Пользователи удалены";
    }

    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "Пользователь удален";
    }

    @Transactional
    public UserPutDto updateUser(Long id, UserPutDto userPutDto) {
        userRepository.updateUser(id, userPutDto.getFirstName(), userPutDto.getLastName(), userPutDto.getPatronymic(),
                userPutDto.getPassport(), userPutDto.getIssueDate(), userPutDto.getIssuePlace());
        userRepository.findById(id).get();
        return userPutDto;
    }
}
