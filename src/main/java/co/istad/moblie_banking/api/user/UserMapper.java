package co.istad.moblie_banking.api.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {
    @InsertProvider(type = UserProvider.class, method ="buildInsertSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(@Param("u") User user);
    @SelectProvider(type = UserProvider.class , method = "buildSelectByIdSql")
    @Result(column = "student_card_id",property = "studentCardId")
    @Result(column = "is_student",property = "isStudent")
    Optional<User> selectById(@Param("id") Integer id);
}
