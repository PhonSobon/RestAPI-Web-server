package co.istad.moblie_banking.api.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {
    @InsertProvider(type = UserProvider.class, method ="buildInsertSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(@Param("u") User user);

    @SelectProvider(type = UserProvider.class, method = "buildSelectByIdSql")
    @Results(id = "userResultMap", value = {
            @Result(column = "student_card_id", property = "studentCardId"),
            @Result(column = "is_student", property = "isStudent")
            })
    Optional<User> selectById(@Param("id") Integer id);

    @SelectProvider(type = UserProvider.class,method = "buildSelectSql")
    @ResultMap("userResultMap")
     List<User> select();
    @Select("SELECT EXISTS(SELECT * FROM users WHERE id = #{id})")
    boolean existById(@Param("id") Integer id);
    @SelectProvider(type = UserProvider.class,method = "buildSelectByNameSql")
    @ResultMap("userResultMap")
    Optional<User> searchByName(@Param("name") String name);
    @SelectProvider(type = UserProvider.class,method = "buildSelectByStudentCardIdSql")
    @ResultMap("userResultMap")
    Optional<User> searchByStudentCardId(@Param("u")String studentCardId);
    @DeleteProvider(type = UserProvider.class ,method = "buildDeleteByIdSql")
    void deleteById(@Param("id") Integer id);
    @UpdateProvider(type = UserProvider.class,method = "buildUpdateIsDeleteStatusByIdSql")
    void updateIsDeletedById(@Param("id") Integer id,@Param("status") boolean status);
    @UpdateProvider(type = UserProvider.class,method = "buildUpdateByIdSql")
    void updateById(@Param("u") User user);

    @Select("SELECT EXISTS(SELECT * FROM users WHERE email=#{email})")
    boolean existByEmail(String email);
    @Select("SELECT EXISTS(SELECT * FROM roles WHERE id=#{roleId})")
    boolean checkRoleById(Integer roleId);

}
