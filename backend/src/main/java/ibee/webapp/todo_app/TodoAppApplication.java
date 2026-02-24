package ibee.webapp.todo_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
public class TodoAppApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    	dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    	SpringApplication.run(TodoAppApplication.class, args);
	}
}
