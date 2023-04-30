package co.istad.moblie_banking.api.user.web;

import co.istad.moblie_banking.api.user.UserService;
import co.istad.moblie_banking.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;


    @PutMapping("/{id}")
    public BaseRest<?> updateUserById(@PathVariable("id") Integer id,@RequestBody UpdateUserDto updateUserDto){
        UserDto userDto =userService.updateUserById(id,updateUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been delete successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }
    @PutMapping("/{id}/is-deleted")
    public BaseRest<?> updateIsDeletedStatusById(@PathVariable Integer id,@RequestBody IsDeletedDto dto){
        Integer deletedId = userService.updateIsDeletedStatusById(id, dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been delete successfully.")
                .timestamp(LocalDateTime.now())
                .data(deletedId)
                .build();
    }
    @DeleteMapping("/{id}")
    public BaseRest<?> deleteUserById(@PathVariable Integer id){
       Integer deletedId = userService.deleteUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been delete successfully.")
                .timestamp(LocalDateTime.now())
                .data(deletedId)
                .build();
    }
    @GetMapping("/{id}")
    public BaseRest<?> findUserById(@PathVariable Integer id){
        UserDto userDto = userService.findUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been create successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();

    }
    @GetMapping
    public BaseRest<?> findAllUsers(@RequestParam(name = "page",required = false,defaultValue = "1")int page,
                                    @RequestParam(name = "limit",required = false,defaultValue = "20")int limit){
        PageInfo<UserDto> userDtoPageInfo = userService.findAllUsers(page,limit);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been Updated successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDtoPageInfo)
                .build();

    }

    @PostMapping
    public BaseRest<?> createNewUser(@RequestBody @Valid CreateUserDto createUserDto){
        UserDto userDto = userService.createNewUser(createUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User has been create successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }
    @GetMapping("/{name}")
    public BaseRest<?> searchUserByName (@PathVariable("name") String name){
        UserDto userDto=userService.searchUserByName(name);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User search name success")
                .data(userDto)
                .build();
    }
    @GetMapping("/{studentId}/student-card-id")
    public BaseRest<?> searchUserByStudentCard(@PathVariable("studentId") String studentId){
        UserDto userDto=userService.searchUserByStudentCard(studentId);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User search by student card success")
                .data(userDto)
                .build();
    }

}
