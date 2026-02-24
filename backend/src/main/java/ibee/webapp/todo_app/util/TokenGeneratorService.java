package ibee.webapp.todo_app.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class TokenGeneratorService {
    public static final int STANDARD_RESET_LENGTH = 32;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public String generateRandomToken(int lengthBytes) {
        byte[] tokenInBytes = new byte[lengthBytes];
        SECURE_RANDOM.nextBytes(tokenInBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenInBytes);
    }
}
