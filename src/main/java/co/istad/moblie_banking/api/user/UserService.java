package co.istad.moblie_banking.api.user;

import co.istad.moblie_banking.api.user.web.CreateUserDto;
import co.istad.moblie_banking.api.user.web.UserDto;

public interface UserService {
        UserDto createNewUser(CreateUserDto createUserDto);
        UserDto findUserById(Integer id);
        Integer deleteUserById(Integer id);
        Integer updateIsDeletedStatusById(Integer id ,boolean status);
}
