package fileManagment.file.constant;

public  class Constant {
    public static final String CHARACTER="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890!@#$%^&*_+=?><";
    public static final int STRENGTH = 12;
    public static final int EXPIRE_DAYS = 90;
    public static final String LOGIN_PATH = "/user/login";
    public static final String EMPTY_VALUE = "empty";
    public static final String AUTHORITIES = "authorities";
    public static final String ROLE = "role";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_DELIMITER =",";
    public static final String ADMIN_AUTHORITIES = "user:create,user:read,user:update,document:create,document:read,document:update,document:delete";
    public static final String SUPER_ADMIN_AUTHORITIES = "user:create,user:read,user:update,user_delete,document:create,document:read,document:update,document:delete";
    public static final String USER_AUTHORITY = "document:create,document:read,document:update,document:delete";
    public static final String MANAGER_AUTHORITY = "document:create,document:read,document:update,document:delete";
    public static final String EPHI = "ephi";
    public static final String TEACHER_AUTHORITY = "teachers:authority";
    public static final String STUDENT_AUTHORITY = "read:time table,read:subject";
    public static final String REGISTRAR_AUTHORITY = "";
}