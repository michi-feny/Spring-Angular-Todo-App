package ibee.webapp.todo_app.security.cookie;

public class JwtSplitter {
    public static String[] splitJwt(String jwt) {
        int lastDot = jwt.lastIndexOf('.');
        if (lastDot == -1) {
            throw new IllegalArgumentException("Invalid JWT format");
        }
        String headerPayload = jwt.substring(0, lastDot);
        String signature = jwt.substring(lastDot + 1);

        return new String[] {headerPayload, signature};
    }
}
