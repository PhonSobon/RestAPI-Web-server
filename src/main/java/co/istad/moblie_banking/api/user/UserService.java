package co.istad.moblie_banking.api.user;

import co.istad.moblie_banking.api.user.web.CreateUserDto;
import co.istad.moblie_banking.api.user.web.UpdateUserDto;
import co.istad.moblie_banking.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;

public interface UserService {
        UserDto createNewUser(CreateUserDto createUserDto);
        UserDto findUserById(Integer id);
        PageInfo<UserDto> findAllUsers(int page,int limit);
        Integer deleteUserById(Integer id);
        Integer updateIsDeletedStatusById(Integer id ,boolean status);
        UserDto updateUserById(Integer id , UpdateUserDto updateUserDto);

}
