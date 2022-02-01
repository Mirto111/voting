package myProject.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("myProject.voting.repository.datajpa")
@EntityScan("myProject.voting.model")
public class VotingApplication {
    public static void main(String[] args) {
        SpringApplication.run(VotingApplication.class, args);
    }
}
