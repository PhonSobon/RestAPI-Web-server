package co.istad.moblie_banking.api.accounttype.web;

import co.istad.moblie_banking.api.accounttype.AccountTypeService;
import co.istad.moblie_banking.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/account-types")
public class AccountRestController {
    private  final AccountTypeService accountTypeService;
    @GetMapping
    BaseRest<?> findAllAccountType(@RequestParam(required = false,name = "page",defaultValue = "1") int page, @RequestParam(required = false,name = "limit",defaultValue = "20") int limit,
            @RequestParam(required = false,name = "name" ,defaultValue = "")String name){
        PageInfo<AccountTypeDto> accountTypeDtoPageInfo=accountTypeService.findAllAccountTpes(page,limit,name);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("AccountTypes have been found")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDtoPageInfo)
                .build();
    }
    @GetMapping("/{id}")
    BaseRest<?> findAccountById(@PathVariable("id") Integer id){
        AccountTypeDto accountTypeDto=accountTypeService.findAccountTypeById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("AccountTypes have been found")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDto)
                .build();
    }
    @PostMapping
    BaseRest<?> createAccountType(@RequestBody @Valid AccountTypeDto accountTypeDto){
        AccountTypeDto accountTypeDtoResult=accountTypeService.createAccountType(accountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("AccountTypes have been insert success")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDtoResult)
                .build();
    }
    @PutMapping("/{id}")
    BaseRest<?> updateAccountTypeById(@PathVariable("id") Integer id,@RequestBody @Valid AccountTypeDto accountTypeDto){
        AccountTypeDto accountTypeDtoResult=accountTypeService.updateAccountTypeById(id,accountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("AccountTypes have been update success")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDtoResult)
                .build();
    }
    @DeleteMapping("/{id}")
    BaseRest<?> deleteAccountTypeById(@PathVariable("id") Integer id){
        Integer accountTypeDtoResult=accountTypeService.deleteAccountTypeById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("AccountTypes have been delete success")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDtoResult)
                .build();
    }
}
