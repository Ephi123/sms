package fileManagment.file.enumeration;

import static fileManagment.file.constant.Constant.*;
import static fileManagment.file.constant.Constant.USER_AUTHORITY;

public enum Authority {
    USER(USER_AUTHORITY),
    ADMIN(ADMIN_AUTHORITIES),
    SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES),
    MANAGER(MANAGER_AUTHORITY),
    TEACHER(TEACHER_AUTHORITY),
    STUDENT(STUDENT_AUTHORITY),
    REGISTRAR(REGISTRAR_AUTHORITY);

    private final String role;
   
    Authority(String role){
        this.role = role;

    }

    public String getRole(){
        return this.role;
    }
}
