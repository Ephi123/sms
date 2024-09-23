package fileManagment.file.util;

import fileManagment.file.entity.CredentialEntity;
import fileManagment.file.entity.RoleEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.securityDto.User;
import org.springframework.beans.BeanUtils;

import java.util.List;

import static fileManagment.file.constant.Constant.EXPIRE_DAYS;
import static fileManagment.file.constant.Constant.ROLE_PREFIX;
import static java.time.LocalDateTime.*;

public class UserDtoUtil {
    public static User convertToUser(UserEntity userEntity, List<RoleEntity> role, CredentialEntity credential) {
          User user=new User();
        BeanUtils.copyProperties(userEntity,user);
        user.setLastLogin(userEntity.getLastLogin().toString());
        user.setCredentialNonExpired(isCredentialNonExpired(credential));
        user.setCreatedAt(userEntity.getCreatedAt().toString());
        user.setUpdateAt(userEntity.getUpdateAt().toString());
        user.setRoles(covertRoleInList(role));
        user.setAuthorities(UserDtoUtil.covertAuthorityInList(role));
        return user;
    }

    private static boolean isCredentialNonExpired(CredentialEntity credential) {
        return credential.getUpdateAt().plusDays(EXPIRE_DAYS).isAfter(now());

    }



    private static  String covertRoleInList(List<RoleEntity> roles){
        StringBuilder res = new StringBuilder();
        var num = 1;
        for (RoleEntity r : roles) {
            res.append(ROLE_PREFIX).append(r.getName());
            if(roles.size() != num){
                res.append(",");

            }
            num++;
        }


        return res.toString();
    }

    private static  String covertAuthorityInList(List<RoleEntity> roles){
        StringBuilder res = new StringBuilder();
        var num = 1;
        for (RoleEntity r : roles) {
            res.append(ROLE_PREFIX).append(r.getAuthority());
            if(roles.size() != num){
                res.append(",");

            }
            num++;
        }


        return res.toString();
    }
}


