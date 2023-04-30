package co.istad.moblie_banking.api.accounttype;

import co.istad.moblie_banking.api.accounttype.web.AccountTypeDto;
import co.istad.moblie_banking.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AccountTypeService {
    PageInfo<AccountTypeDto> findAllAccountTpes(int page,int limit,String name);
    AccountTypeDto findAccountTypeById(Integer id);
    AccountTypeDto createAccountType(AccountTypeDto accountTypeDto);
    AccountTypeDto updateAccountTypeById(Integer id,AccountTypeDto accountTypeDto);
    Integer deleteById(Integer id);
}
