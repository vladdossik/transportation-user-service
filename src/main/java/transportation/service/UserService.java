package transportation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import transportation.exception.EntityAlreadyExistsException;
import transportation.exception.EntityNotFoundException;
import transportation.mapper.UserMapper;
import transportation.model.User;
import transportation.repository.UserRepository;
import users.UserPageResponse;
import users.UserPostDto;
import users.UserPutDto;
import users.UserResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    public UserPageResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, Sort.Direction direction, String firstName, String lastName) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, direction, sortBy);
        if (firstName != null && lastName != null) {
            return userMapper.toPageResponseDto(userRepository.getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(pageable, firstName, lastName), sortBy, direction);
        } else if (firstName != null) {
            return userMapper.toPageResponseDto(userRepository.getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCase(pageable, firstName), sortBy, direction);
        } else if (lastName != null) {
            return userMapper.toPageResponseDto(userRepository.getAllByDeletionDateIsNullAndLastNameContainsIgnoreCase(pageable, lastName), sortBy, direction);
        } else
            return userMapper.toPageResponseDto(userRepository.getAllByDeletionDateIsNull(pageable), sortBy, direction);
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
                userPutDto.getPassport(), LocalDate.parse(userPutDto.getIssueDate()), userPutDto.getIssuePlace());
        userRepository.findById(id).get();
        return userPutDto;
    }

    public UserResponseDto reestablish(Long id){
        userRepository.reestablish(id);
        Optional<User> user = userRepository.findByIdAndDeletionDateIsNull(id);
        if (user.isPresent()) {
            return userMapper.toResponseDto(user.get());
        } else throw new EntityNotFoundException("User with id = " + id + " not found");
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
