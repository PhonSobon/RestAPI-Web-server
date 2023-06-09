package co.istad.moblie_banking.api.auth;

import co.istad.moblie_banking.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AuthMapper {
    @InsertProvider(type = AuthProvider.class, method = "buildRegisterSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    boolean register(@Param("u") User user);

    @InsertProvider(type = AuthProvider.class,method = "buildCreateUseRoleSql")
    void createUserRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);


    @Select("SELECT * FROM users WHERE email = #{email} AND is_deleted = FALSE")
    @Results(id = "authResultMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "student_card_id", property = "studentCardId"),
            @Result(column = "is_student", property = "isStudent"),
            @Result(column = "is_verified",property = "isVerified"),
            @Result(column = "verified_code",property = "verifiedCode"),
            @Result(column = "id",property = "roles",many = @Many(select = "loadUserRoles"))
    })
    Optional<User> selectByEmail(@Param("email") String email);


    @SelectProvider(type = AuthProvider.class,method = "buildSelectByEmailAndVerifiedCodeSql")
    @ResultMap(value = "authResultMap")
    Optional<User> selectByEmailAndVerifiedCode(@Param("email") String email,@Param("verifiedCode") String verifiedCode);
    @UpdateProvider(type = AuthProvider.class,method = "buildVerifySql")
    void verify(@Param("email") String email,@Param("verifiedCode") String verifiedCode);
    @UpdateProvider(type = AuthProvider.class,method = "buildUpdateVerifiedCodeSql")
    boolean updateVerifiedCode(@Param("email") String email,@Param("verifiedCode") String verifiedCode);

    @Select("SELECT * FROM users WHERE email = #{email} AND is_verified=true")
    @ResultMap( "authResultMap")
    Optional<User> loadUserByUsername(@Param("email") String email);
    @SelectProvider(type = AuthProvider.class,method = "buildLoadUserRoleSql")
    List<Role> loadUserRoles(@Param("id") Integer id);


}
