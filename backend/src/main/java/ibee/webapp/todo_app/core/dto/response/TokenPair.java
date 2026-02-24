package ibee.webapp.todo_app.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenPair(
        @Nullable String accessToken,
        @Nullable String refreshToken,
        Date refreshTokenExp,
        Date accessTokenExp
) {
    public TokenPair clearTokens() {
        return new TokenPair(null, null, this.refreshTokenExp, this.accessTokenExp);
    }
}