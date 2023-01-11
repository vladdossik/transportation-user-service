package transportation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import transportation.service.UserService;
import users.UserPostRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("all")
    public List <UserPostRequest> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserPostRequest getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("add")
    public UserPostRequest addUser(@RequestBody UserPostRequest user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("{id}/delete")
    public void deleteUsers(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @DeleteMapping("delete")
    public void
}
