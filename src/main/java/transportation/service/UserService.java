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
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto save(UserPostDto userPostDto) {
        checkExistenceByPassportAndThrow(userPostDto.getPassport());
        User user = User.builder()
                .externalId(UUID.randomUUID())
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

    public UserResponseDto getById(UUID externalId) {
        Optional<User> user = userRepository.findByExternalIdAndDeletionDateIsNull(externalId);
        if (user.isPresent()) {
            return userMapper.toResponseDto(user.get());
        } else throw new EntityNotFoundException("User with id = " + externalId + " not found");
    }

    public String deleteAll() {
        userRepository.deleteAll();
        return "Пользователи удалены";
    }

    @Transactional
    public String delete(UUID externalId) {
        userRepository.delete(externalId);
        return "Пользователь удален";
    }

    @Transactional
    public UserResponseDto update(UUID externalId, UserPutDto userPutDto) {
        checkExistenceOrThrow(externalId);
        checkExistenceByPassportAndThrow(userPutDto.getPassport());

        userRepository.update(externalId, userPutDto.getFirstName(), userPutDto.getLastName(), userPutDto.getPatronymic(),
                userPutDto.getPassport(), LocalDate.parse(userPutDto.getIssueDate()), userPutDto.getIssuePlace());
        return userMapper.toResponseDto(userRepository.findByExternalId(externalId).get());
    }

    public UserResponseDto reestablish(UUID externalId){
        userRepository.reestablish(externalId);
        Optional<User> user = userRepository.findByExternalIdAndDeletionDateIsNull(externalId);
        if (user.isPresent()) {
            return userMapper.toResponseDto(user.get());
        } else throw new EntityNotFoundException("User with id = " + externalId + " not found");
    }

    private void checkExistenceOrThrow(UUID externalId) {
        Optional<User> user = userRepository.findByExternalIdAndDeletionDateIsNull(externalId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with id = " + externalId + " not found");
        }
    }

    private void checkExistenceByPassportAndThrow(String passport) {
        Optional<User> user = userRepository.findByPassport(passport);
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException("user with passport already exist");
        }
    }
}
