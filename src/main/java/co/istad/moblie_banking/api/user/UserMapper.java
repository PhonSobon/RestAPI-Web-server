package co.istad.moblie_banking.api.user;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @InsertProvider(type = UserProvider.class, method ="buildInsertSql")
    void insert(@Param("u") User user);
}
