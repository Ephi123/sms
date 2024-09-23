package fileManagment.file.domain;

public class RequestContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    public RequestContext(){}

    public static void start(){
        USER_ID.remove();
    }

    public static Long getUserId(){
         return USER_ID.get();
    }

    public static void setUserId(Long userId){
        USER_ID.set(userId);
    }

}
