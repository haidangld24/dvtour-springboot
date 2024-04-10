package project.dvtour.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "ZHZ0b3Vyc2VjcmV0a2V5J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)"; 
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 mil s = 7200 sec
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token 
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "/user/register"; 
}
