package co.istad.moblie_banking.api.user;

import co.istad.moblie_banking.api.auth.web.RegisterDto;
import co.istad.moblie_banking.api.user.web.CreateUserDto;
import co.istad.moblie_banking.api.user.web.UpdateUserDto;
import co.istad.moblie_banking.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    User createUserDtoToUser(CreateUserDto createUserDto);
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    User  updateUserDtoToUser(UpdateUserDto updateUserDto);
    PageInfo<UserDto> userPageInfoToUserDtoPageInfo(PageInfo<User> userPageInfo);
    User registerDtoToUser(RegisterDto registerDto);
}
