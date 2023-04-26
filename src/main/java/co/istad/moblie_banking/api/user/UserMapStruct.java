package co.istad.moblie_banking.api.user;

import co.istad.moblie_banking.api.user.web.CreateUserDto;
import co.istad.moblie_banking.api.user.web.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    User createUserDtoToUser(CreateUserDto createUserDto);
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
