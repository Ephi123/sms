package fileManagment.file.util;

public class EmailUtil {
    public static String  getEmailMessage(String name, String host, String token, String password){
        return "Hello " + name +"\n\n your password is " + password + ",\n\nThe new account has been created. Click the link below \n\n"+
                getAccountVerifyUrl(host,token);
    }

    private static String getAccountVerifyUrl(String host, String token) {
        return host + "/user/verify/account?token=" + token;
    }


    public static String  getPasswordMessage(String name, String host, String token){
        return "Hello " + name + ",\n\nTo reset the password,Click the link below \n\n"+
                getPasswordVerifyUrl(host,token);
    }

    private static String getPasswordVerifyUrl(String host, String token) {
        return host + "/user/verify/password?token=" + token;
    }
}
