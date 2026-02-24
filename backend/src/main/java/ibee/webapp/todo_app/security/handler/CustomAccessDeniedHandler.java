package ibee.webapp.todo_app.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record CustomAccessDeniedHandler(ObjectMapper objectMapper, TranslationService translationService, @Value("${spring.application.api-url}") String API_URL) implements AccessDeniedHandler {
    @Override
    public void handle(
            @NotNull  HttpServletRequest request,
            HttpServletResponse response,
            @NotNull AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setType(URI.create(API_URL + "errors/access-denied"));
        problemDetail.setTitle("Access Denied");
        problemDetail.setDetail(translationService.translate("access.denied"));

        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("timestamp", Instant.now());
        problemDetail.setProperties(properties);

        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        this.objectMapper.writeValue(response.getWriter(), problemDetail);
    }
}