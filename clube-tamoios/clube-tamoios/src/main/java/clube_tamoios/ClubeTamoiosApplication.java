package clube_tamoios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClubeTamoiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubeTamoiosApplication.class, args);
	}

}
