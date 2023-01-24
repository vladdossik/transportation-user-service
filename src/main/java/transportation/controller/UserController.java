package transportation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import transportation.service.UserService;
import users.UserPageResponse;
import users.UserPostDto;
import users.UserPutDto;
import users.UserResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Добавить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибочный запрос"),
            @ApiResponse(responseCode = "409", description = "Запись уже существует"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")
    })
    @PostMapping("add")
    public UserResponseDto add(@Valid @RequestBody UserPostDto userPostDto) {
        return userService.save(userPostDto);
    }

    @Operation(summary = "Получить список пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Ошибочный запрос"),
            @ApiResponse(responseCode = "409", description = "Запись уже существует"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")
    })
    @GetMapping("all")
    public UserPageResponse getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam Sort.Direction direction,
            @RequestParam(required = false) String firstNameFilter,
            @RequestParam(required = false) String lastNameFilter
            ) {
        return userService.getAll(pageNumber, pageSize, sortBy, direction, firstNameFilter, lastNameFilter);
    }

    @Operation(summary = "Получить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибочный запрос"),
            @ApiResponse(responseCode = "409", description = "Запись уже существует"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")
    })
    @GetMapping("{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Удалить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Ошибочный запрос"),
            @ApiResponse(responseCode = "409", description = "Запись уже существует"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")
    })
    @DeleteMapping("{id}/delete")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Удалить всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Ошибочный запрос"),
            @ApiResponse(responseCode = "409", description = "Запись уже существует"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")
    })
    @DeleteMapping("delete")
    public String deleteAll() {
        return userService.deleteAll();
    }

    @Operation(summary = "Обновить данные пользователея")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибочный запрос"),
            @ApiResponse(responseCode = "409", description = "Запись уже существует"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен")
    })
    @PutMapping("{id}")
    public UserPutDto update(@PathVariable Long id, @Valid @RequestBody UserPutDto userDto) {
        return userService.update(id, userDto);
    }
}
