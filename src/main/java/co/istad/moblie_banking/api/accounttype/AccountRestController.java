package co.istad.moblie_banking.api.accounttype;

import co.istad.moblie_banking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account-types")
public class AccountRestController {
    private  final  AccountTypeService accountTypeService;
    @GetMapping
    public BaseRest<?> findAll(){
        var accountDtoList= accountTypeService.findAll();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account type have food")
                .timestamp(LocalDateTime.now())
                .data(accountDtoList)
                .build();
    }
}
