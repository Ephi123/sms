package fileManagment.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static fileManagment.file.constant.Constant.ROLE_PREFIX;

@Getter
@Setter
@Builder
class Rol{
    private String name;
    private String authority;

}

public class Demo {
         public static void main(String [] args){

             LocalDateTime  lastUpdateTime = LocalDateTime.now().minusDays(80) ;

             var rol1 = Rol.builder().name("user").authority("u.p1,u.p2,u.p3").build();
             var rol2 = Rol.builder().name("Admin").authority("a.p1,a.p2").build();

             List<Rol> rols = List.of(rol2,rol1);
             System.out.println(new BCryptPasswordEncoder().encode("123"));

         }

         private static String converter(List<Rol> rols) {
             StringBuilder res = new StringBuilder();
             var num = 1;
             for (Rol r : rols) {
                 res.append(ROLE_PREFIX).append(r.getName());
                   if(rols.size() != num){
                       res.append(",");

                   }
                 num++;
                 }


             return res.toString();
         }



}



