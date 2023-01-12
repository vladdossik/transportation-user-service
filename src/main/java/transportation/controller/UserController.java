package transportation.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import transportation.dto.UserPostDto;
import transportation.dto.UserPutDto;
import transportation.dto.UserResponseDto;
import transportation.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("add")
    public UserResponseDto addUser(@RequestBody UserPostDto userPostDto) {
        return userService.saveUser(userPostDto);
    }

    @GetMapping("all")
    public List <UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("{id}/delete")
    public void deleteUsers(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @DeleteMapping("delete")
    public String deleteUserAll() {
        return userService.deleteAll();
    }

    @PutMapping("{id}")
    public UserPutDto updateUser(@PathVariable Long id, @RequestBody UserPutDto userDto) {
        return userService.updateUser(id, userDto);
    }
}
