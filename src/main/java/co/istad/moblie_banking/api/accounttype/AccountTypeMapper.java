package co.istad.moblie_banking.api.accounttype;

import com.github.pagehelper.ISelect;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Mapper
public interface AccountTypeMapper {
    @InsertProvider(type = AccountTypeProvider.class,method = "buildInsertSql")
    void insert(@Param("a") AccountType accountType);
    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelectByIdSql")
    Optional<AccountType> findAccountTypeById(@Param("id") Integer id);
    @SelectProvider(type = AccountTypeProvider.class,method = "buildSelectSql")
    ISelect selectByName(@Param("name") String name);
    @InsertProvider(type = AccountTypeProvider.class,method = "buildUpdateSql")
    void updateById(@Param("a") AccountType accountType);
    @Select(" SELECT EXISTS (SELECT *FROM account_types where id=#{id})")
    boolean isIdExist(@Param("id") Integer id);
    @DeleteProvider(type = AccountTypeProvider.class,method = "buildDeleteSql")
    void delete(@Param("id") Integer id);

}
