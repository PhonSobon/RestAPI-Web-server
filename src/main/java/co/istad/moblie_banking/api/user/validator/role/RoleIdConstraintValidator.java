package co.istad.moblie_banking.api.user.validator.role;

import co.istad.moblie_banking.api.user.UserMapper;
import co.istad.moblie_banking.api.user.validator.role.RoleIdConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class RoleIdConstraintValidator implements ConstraintValidator<RoleIdConstraint, List<Integer>> {
    private final UserMapper userMapper;
    @Override
    public boolean isValid(List<Integer> roleIds, ConstraintValidatorContext context) {
        for(Integer roleId :roleIds ) {
            if(!userMapper.checkRoleById(roleId)){
                return false;
            }
        }
        return true;
    }
}
