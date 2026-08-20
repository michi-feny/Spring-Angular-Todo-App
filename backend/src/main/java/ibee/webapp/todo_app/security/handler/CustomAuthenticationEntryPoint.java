package ibee.webapp.todo_app.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final TranslationService translationService;
    @Value("${spring.application.api-url}")
    private String API_URL;

    public CustomAuthenticationEntryPoint(
            ObjectMapper objectMapper,
            TranslationService translationService,
            @Value("${spring.application.api-url}") String apiUrl) {
        this.objectMapper = objectMapper;
        this.translationService = translationService;
        this.API_URL = apiUrl;
    }

    @Override
    public void commence(
            @NotNull HttpServletRequest request,
            HttpServletResponse response,
            @NotNull AuthenticationException authException
    ) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                translationService.translate("unauthorized")
        );
        problemDetail.setTitle("Unauthorized Access");
        problemDetail.setType(URI.create("https://api.ihreapp.de/errors/unauthorized"));
        problemDetail.setProperty("timestamp", Instant.now());

        objectMapper.writeValue(response.getWriter(), problemDetail);
    }
}