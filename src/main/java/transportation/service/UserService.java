package transportation.service;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import transportation.exception.EntityAlreadyExistsException;
import transportation.exception.EntityNotFoundException;
import transportation.mapper.UserMapper;
import transportation.model.User;
import transportation.repository.UserRepository;
import users.UserPostDto;
import users.UserPutDto;
import users.UserResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto save(UserPostDto userPostDto) {
        checkExistenceByPassportAndThrow(userPostDto.getPassport());
        User user = User.builder()
                .firstName(userPostDto.getFirstName())
                .lastName(userPostDto.getLastName())
                .patronymic(userPostDto.getPatronymic())
                .passport(userPostDto.getPassport())
                .issueDate(LocalDate.parse(userPostDto.getIssueDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .creationDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .issuePlace(userPostDto.getIssuePlace()).build();
        return userMapper.toResponseDto(userRepository.save(user));
    }

    public List<UserResponseDto> getAll() {
        List<User> list = userRepository.getAllByDeletionDateIsNull();
        return list.stream().map(userMapper::toResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto getById(Long id) {
        Optional<User> user = userRepository.findByIdAndDeletionDateIsNull(id);
        if (user.isPresent()) {
            return userMapper.toResponseDto(user.get());
        } else throw new EntityNotFoundException("User with id = " + id + " not found");
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
        checkExistenceOrThrow(id);
        checkExistenceByPassportAndThrow(userPutDto.getPassport());

        userRepository.update(id, userPutDto.getFirstName(), userPutDto.getLastName(), userPutDto.getPatronymic(),
                userPutDto.getPassport(), userPutDto.getIssueDate(), userPutDto.getIssuePlace());
        userRepository.findById(id).get();
        return userPutDto;
    }

    private void checkExistenceOrThrow(Long id) {
        Optional<User> user = userRepository.findByIdAndDeletionDateIsNull(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with id = " + id + " not found");
        }
    }

    private void checkExistenceByPassportAndThrow(String passport) {
        Optional<User> user = userRepository.findByPassport(passport);
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException("user with passport already exist");
        }
    }
}
