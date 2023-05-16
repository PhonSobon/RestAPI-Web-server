package co.istad.moblie_banking.api.user;

import co.istad.moblie_banking.api.auth.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String gender;
    private String oneSignalId;
    private String studentCardId;
    private Boolean isStudent;
    private Boolean isDeleted;

    // Auth feature info
    private String email;
    private String password;
    private Boolean isVerified;
    private String verifiedCode;

    //user has role
    private List<Role> roles;

}
