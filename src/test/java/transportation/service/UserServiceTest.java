package transportation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import transportation.AbstractInitialization;
import transportation.exception.EntityAlreadyExistsException;
import transportation.exception.EntityNotFoundException;
import transportation.mapper.UserMapper;
import transportation.model.User;
import transportation.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest extends AbstractInitialization {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserByIdTest() {
        Mockito.when(userRepository.findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(user));
        Mockito.when(userMapper.toResponseDto(user))
                .thenReturn(userResponseDto);

        userService.getById(UUID.randomUUID());

        Mockito.verify(userRepository, Mockito.times(1))
                .findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class));
        Mockito.verify(userMapper, Mockito.times(1))
                .toResponseDto(Mockito.any(User.class));
    }

    @Test
    void getUserByIdNotFoundTest() {
        Mockito.when(userRepository.findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(Mockito.any(UUID.class)))
                .isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(userMapper, Mockito.times(0))
                .toResponseDto(Mockito.any(User.class));
    }

    @Test
    void updateUserTest() {
        Mockito.when(userRepository.findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByPassport(Mockito.anyString()))
                .thenReturn(Optional.empty());
        Mockito.doNothing().when(userRepository).update(
                Mockito.any(UUID.class),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDate.class),
                Mockito.anyString());
        Mockito.when(userRepository.findByExternalId(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(user));
        Mockito.when(userMapper.toResponseDto(Mockito.any(User.class)))
                .thenReturn(userResponseDto);

        userService.update(userExternalId, userPutDto);

        Mockito.verify(userRepository, Mockito.times(1))
                .findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class));
        Mockito.verify(userRepository, Mockito.times(1))
                .findByPassport(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1))
                .findByExternalId(Mockito.any(UUID.class));
        Mockito.verify(userMapper, Mockito.times(1))
                .toResponseDto(Mockito.any(User.class));
    }

    @Test
    void updateUserNotFoundTest() {
        Mockito.when(userRepository.findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(userExternalId, userPutDto))
                .isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(userRepository, Mockito.times(1))
                .findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class));
    }

    @Test
    void updateUserConflictByPassportTest() {
        Mockito.when(userRepository.findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByPassport(Mockito.anyString()))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.update(userExternalId, userPutDto))
                .isInstanceOf(EntityAlreadyExistsException.class);

        Mockito.verify(userRepository, Mockito.times(1))
                .findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class));
        Mockito.verify(userRepository, Mockito.times(1))
                .findByPassport(Mockito.anyString());
    }

    @Test
    void deleteUserTest() {
        Mockito.doNothing().when(userRepository).delete(Mockito.any(UUID.class));
        assertEquals(userService.delete(userExternalId), "Пользователь удален");
    }

    @Test
    void deleteUsersTest() {
        Mockito.doNothing().when(userRepository).deleteAll();
        assertEquals(userService.deleteAll(), "Пользователи удалены");
    }

    @Test
    void saveUserTest() {
        Mockito.when(userRepository.findByPassport(Mockito.anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);
        Mockito.when(userMapper.toResponseDto(Mockito.any(User.class)))
                .thenReturn(userResponseDto);

        userService.save(userPostDto);

        Mockito.verify(userRepository, Mockito.times(1))
                .findByPassport(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1))
                .save(Mockito.any(User.class));
        Mockito.verify(userMapper, Mockito.times(1))
                .toResponseDto(Mockito.any(User.class));
    }

    @Test
    void reestablishUserTest() {
        Mockito.doNothing().when(userRepository).reestablish(Mockito.any(UUID.class));
        Mockito.when(userRepository.findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(user));
        Mockito.when(userMapper.toResponseDto(user))
                .thenReturn(userResponseDto);

        userService.reestablish(userExternalId);

        Mockito.verify(userRepository, Mockito.times(1))
                .reestablish(Mockito.any(UUID.class));
        Mockito.verify(userRepository, Mockito.times(1))
                .findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class));
        Mockito.verify(userMapper, Mockito.times(1))
                .toResponseDto(Mockito.any(User.class));
    }

    @Test
    void reestablishUserNotFoundTest() {
        Mockito.when(userRepository.findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.reestablish(userExternalId))
                .isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(userRepository, Mockito.times(1))
                .findByExternalIdAndDeletionDateIsNull(Mockito.any(UUID.class));
    }

    @Test
    void getAllUsersWithFirstNameAndLastNameTest() {
        Mockito.when(userRepository.getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(
                        Mockito.any(Pageable.class), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(page);
        Mockito.when(userMapper.toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class)))
                .thenReturn(userPageResponse);

        userService.getAll(page.getNumber(), page.getSize(), userPageResponse.getSortBy(), Sort.Direction.ASC, user.getFirstName(), user.getLastName());

        Mockito.verify(userRepository, Mockito.times(1))
                .getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(Mockito.any(Pageable.class), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(1))
                .toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class));
    }

    @Test
    void getAllUsersWithoutLastNameTest() {
        Mockito.when(userRepository.getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCase(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(page);
        Mockito.when(userMapper.toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class)))
                .thenReturn(userPageResponse);

        userService.getAll(page.getNumber(), page.getSize(), userPageResponse.getSortBy(), Sort.Direction.ASC, user.getFirstName(), null);

        Mockito.verify(userRepository, Mockito.times(1))
                .getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCase(Mockito.any(Pageable.class), Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(1))
                .toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class));
    }

    @Test
    void getAllUsersWithoutFirstNameTest() {
        Mockito.when(userRepository.getAllByDeletionDateIsNullAndLastNameContainsIgnoreCase(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(page);
        Mockito.when(userMapper.toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class)))
                .thenReturn(userPageResponse);

        userService.getAll(page.getNumber(), page.getSize(), userPageResponse.getSortBy(), Sort.Direction.ASC, null, user.getLastName());

        Mockito.verify(userRepository, Mockito.times(1))
                .getAllByDeletionDateIsNullAndLastNameContainsIgnoreCase(Mockito.any(Pageable.class), Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(1))
                .toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class));
    }

    @Test
    void getAllUsersWithoutFirstNameAndLastNameTest() {
        Mockito.when(userRepository.getAllByDeletionDateIsNull(Mockito.any(Pageable.class)))
                .thenReturn(page);
        Mockito.when(userMapper.toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class)))
                .thenReturn(userPageResponse);

        userService.getAll(page.getNumber(), page.getSize(), userPageResponse.getSortBy(), Sort.Direction.ASC, null, null);

        Mockito.verify(userRepository, Mockito.times(1))
                .getAllByDeletionDateIsNull(Mockito.any(Pageable.class));
        Mockito.verify(userMapper, Mockito.times(1))
                .toPageResponseDto(Mockito.any(Page.class), Mockito.anyString(), Mockito.any(Sort.Direction.class));
    }
}
