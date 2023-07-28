package owt.training.fluentd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class FluentdApplication {

	private static final Logger log = LoggerFactory.getLogger(FluentdApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FluentdApplication.class, args);
	}

	@Scheduled(fixedRate = 5000)
	public void log() {
		log.info("This is \\na multiline\\n\\n\\nlog");
	}

	@Scheduled(fixedRate = 10000)
	public void logException() {
		try {
			String a = null;
			log.info(a.toLowerCase());
		} catch (NullPointerException e) {
			throw new RuntimeException("Error happened", e);
		}
	}
}
