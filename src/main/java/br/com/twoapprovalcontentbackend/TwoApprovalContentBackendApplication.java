package br.com.twoapprovalcontentbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.TimeZone;

@EnableMongoAuditing
@SpringBootApplication
public class TwoApprovalContentBackendApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        SpringApplication.run(TwoApprovalContentBackendApplication.class, args);
    }

}
