package co.istad.moblie_banking.security;

import co.istad.moblie_banking.api.auth.AuthMapper;
import co.istad.moblie_banking.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authMapper.loadUserByUsername(username).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "User is not valid"));
        CustomUserDetails customUserDetails =new CustomUserDetails();
        customUserDetails.setUser(user);
        return customUserDetails;
    }
}
