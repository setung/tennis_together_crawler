package kr.couchcoding.tennis_together_crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TennisTogetherCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisTogetherCrawlerApplication.class, args);
	}

}
