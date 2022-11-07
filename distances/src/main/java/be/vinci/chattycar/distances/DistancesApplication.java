package be.vinci.chattycar.distances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DistancesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistancesApplication.class, args);

    }

}
