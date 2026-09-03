package ibee.webapp.todo_app;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mariadb://localhost:3308/todo_test");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "your_strong_root_password");
        registry.add("spring.datasource.driver-class-name", () -> "org.mariadb.jdbc.Driver");
        
        registry.add("jakarta.persistence.jdbc.url", () -> "jdbc:mariadb://localhost:3308/todo_test");
        registry.add("jakarta.persistence.jdbc.user", () -> "root");
        registry.add("jakarta.persistence.jdbc.password", () -> "your_strong_root_password");
        registry.add("jakarta.persistence.jdbc.driver", () -> "org.mariadb.jdbc.Driver");

        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MariaDBDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("jwt.secret.key", () -> "4de22fc54d5239beb2fc60cf9081ba8838cddea3ec66dcc3e4a242035b1850a31f34dd61d8e26f8f6730eafa198f39651bf50ee89a556b39374be5507ed5036f");
    }
}
