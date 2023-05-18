package co.istad.moblie_banking.api.auth;

import co.istad.moblie_banking.api.auth.web.AuthDto;
import co.istad.moblie_banking.api.auth.web.LogInDto;
import co.istad.moblie_banking.api.auth.web.RegisterDto;
import co.istad.moblie_banking.api.user.User;
import co.istad.moblie_banking.api.user.UserMapStruct;
import co.istad.moblie_banking.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtil mailUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    @Value("${spring.mail.username}")
    private String appMail;

    @Override
    public AuthDto login(LogInDto logInDto) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(logInDto.email(), logInDto.password());

        authentication = daoAuthenticationProvider.authenticate(authentication);

        log.info("Authentication: {}", authentication);
        log.info("Authentication: {}", authentication.getName());
        log.info("Authentication: {}", authentication.getCredentials());

        // Logic on basic authorization header
        String basicAuthFormat = authentication.getName() + ":" + authentication.getCredentials();
        String encoding = Base64.getEncoder().encodeToString(basicAuthFormat.getBytes());

        log.info("Basic {}", encoding);

        return new AuthDto(String.format("Basic %s", encoding));
    }

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        User user = userMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));
        log.info("password: {}", user.getPassword());
        log.info("users:{}",user.getEmail());
        if(authMapper.register(user)){
            //create user role
            for(Integer role : registerDto.roleIds()){
                authMapper.createUserRole(user.getId(), role);
            }
        }


    }

    @Override
    public void verify(String email) {
        User user = authMapper.selectByEmail(email).orElseThrow(()
                -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                "Email has not been found")
        );
        String verifiedCode =UUID.randomUUID().toString();
        if(authMapper.updateVerifiedCode(email,verifiedCode)){
            user.setVerifiedCode(verifiedCode);
        }else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Users can't be verified");
        }
        MailUtil.Meta<?> meta = MailUtil.Meta.builder()
                .to(email)
                .from(appMail)
                .subject("Account Verification")
                .templateUrl("auth/verify")
                .data(user)
                .build();

        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    @Override
    public void checkVerify(String email, String verifiedCode) {
        User user =authMapper.selectByEmailAndVerifiedCode(email,verifiedCode)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Users not exist in the database"));
        if(!user.getIsVerified()){
            authMapper.verify(email,verifiedCode);
        }

    }

}
