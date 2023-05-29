package project.mini.telegramwebhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramWebhookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramWebhookApplication.class, args);
		// SpringApplication app = new SpringApplication(CustomApplication.class);
        // app.setDefaultProperties(Collections
        //   .singletonMap("server.port", "8443"));
        // app.run(args);
	}

}
