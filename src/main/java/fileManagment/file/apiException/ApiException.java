package fileManagment.file.apiException;

public class ApiException extends RuntimeException{
       public ApiException(String message){
           super(message);
       }

    public ApiException(){
        super("error occurred");
    }
}
