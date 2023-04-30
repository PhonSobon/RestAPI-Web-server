package co.istad.moblie_banking.api.accounttype;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    private static final String tableName="account_types";
    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO(tableName);
            VALUES("name","#{a.name}");
        }}.toString();
    }
    public String buildUpdateSql(){
        return new SQL(){{
            UPDATE(tableName);
            SET("name=#{a.name}");
            WHERE("id=#{a.id}");
        }}.toString();
    }
    public String buildDeleteSql(){
        return new SQL(){{
            DELETE_FROM(tableName);
            WHERE("id=#{id}");
        }}.toString();
    }
    public String buildSelectSql(@Param("name") String name) {
        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE("name ILIKE CONCAT('%',#{name},'%')");
        }}.toString();
    }
    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("id=#{id}");
        }}.toString();
    }



}

