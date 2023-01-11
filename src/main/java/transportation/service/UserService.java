package transportation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transportation.repository.UserRepository;
import users.UserPostRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserPostRequest saveUser(UserPostRequest userPostRequest) {
        UserPostRequest user = new UserPostRequest();
        //не получается загрузить сеттеры
        return userRepository.save(user);
    }
    public List<UserPostRequest> getAllUsers() {
        return userRepository.findAll();
    }
    public UserPostRequest getById(Long id) {
        return userRepository.getById(id);
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
    public UserPostRequest updateUser(Long id, UserPostRequest user) {
        userRepository.updateUser(id, user.getFirstName());
        userRepository.findById(id).get();
        return user;
    }
}
