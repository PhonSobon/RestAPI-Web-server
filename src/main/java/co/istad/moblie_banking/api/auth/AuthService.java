package co.istad.moblie_banking.api.auth;

import co.istad.moblie_banking.api.auth.web.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);

    void verify(String email);
    void checkVerify(String email,String verifiedCode);
}
