package be.vinci.ipl.chattycar.mock_users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MockUsersApplication {

  public static void main(String[] args) {
    SpringApplication.run(MockUsersApplication.class, args);
  }

}
