package transportation.service;

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
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto saveUser(UserPostDto userPostDto) {
        User user = new User();
        user.setFirstName(userPostDto.getFirstName());
        user.setLastName(userPostDto.getLastName());
        user.setPatronymic(userPostDto.getPatronymic());
        user.setPassport(userPostDto.getPassport());
        user.setIssueDate(userPostDto.getIssueDate());
        user.setIssuePlace(userPostDto.getIssuePlace());
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
        userRepository.deleteAll();
        return "Пользователи удалены";
    }
    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "Пользователь удален";
    }
    @Transactional
    public UserPutDto updateUser(Long id, UserPutDto userPutDto) {
        userRepository.updateUser(id, userPutDto.getFirstName(), userPutDto.getLastName(), userPutDto.getPatronymic(),
                userPutDto.getPassport(), userPutDto.getIssueDate(), userPutDto.getIssuePlace(), userPutDto.getAmountOfOrders());
        userRepository.findById(id).get();
        return userPutDto;
    }
}
