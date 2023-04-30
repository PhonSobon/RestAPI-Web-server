package co.istad.moblie_banking.api.accounttype;

import co.istad.moblie_banking.api.accounttype.web.AccountTypeDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapStruct {
    AccountTypeDto accountTypeToAccountTypeDto(AccountType accountType);
    AccountType createAccountType(AccountTypeDto accountTypeDto);
    PageInfo<AccountTypeDto> acccountTypePageToAccountTypeDtoPageInfo(PageInfo<AccountType> accountTypePageInfo);

}

