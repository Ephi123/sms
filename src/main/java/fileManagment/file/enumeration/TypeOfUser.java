package fileManagment.file.enumeration;

import lombok.Getter;

@Getter
public enum TypeOfUser {
    STUDENT("STU"), EMPLOYEE("EMP");

    private final String value;
     TypeOfUser(String value){
        this.value = value;
    }


}
