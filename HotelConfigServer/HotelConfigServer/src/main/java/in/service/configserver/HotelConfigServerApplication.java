package in.service.configserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class HotelConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelConfigServerApplication.class, args);
	}
}
