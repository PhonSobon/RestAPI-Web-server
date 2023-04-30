package co.istad.moblie_banking.api.accounttype;

import co.istad.moblie_banking.api.accounttype.web.AccountTypeDto;
import co.istad.moblie_banking.api.user.User;
import co.istad.moblie_banking.api.user.web.UserDto;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RequiredArgsConstructor
@Service
public class AccountTypeServiceImpl implements AccountTypeService{
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;
    @Override
    public AccountTypeDto createAccountType(AccountTypeDto accountTypeDto) {
        AccountType accountType=accountTypeMapStruct.createAccountType(accountTypeDto);
        accountTypeMapper.insert(accountType);
        return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
    }

    @Override
    public PageInfo<AccountTypeDto> findAllAccountTpes(int page, int limit, String name) {
        PageInfo<AccountType> accountTypePageInfo= PageHelper.startPage(page,limit).doSelectPageInfo(
                () -> accountTypeMapper.selectByName(name)
        );
        return accountTypeMapStruct.acccountTypePageToAccountTypeDtoPageInfo(accountTypePageInfo);
    }
    @Override
    public AccountTypeDto findAccountTypeById(Integer id) {
        AccountType accountType=accountTypeMapper.findAccountTypeById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("AccountType id %d is not found",id)));
        return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
    }


    @Override
    public AccountTypeDto updateAccountTypeById(Integer id, AccountTypeDto accountTypeDto) {
        AccountType accountType=accountTypeMapper.findAccountTypeById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("AccountType id %d is not found",id)));
        accountType=accountTypeMapStruct.createAccountType(accountTypeDto);
        accountType.setId(id);
        accountTypeMapper.updateById(accountType);
        System.out.println(accountType);
        return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
    }

    @Override
    public Integer deleteAccountTypeById(Integer id) {
        boolean isIdExist=accountTypeMapper.isIdExist(id);
        if(isIdExist){
            accountTypeMapper.delete(id);
            return id;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("AccountType id %d is not found",id));
    }
}
