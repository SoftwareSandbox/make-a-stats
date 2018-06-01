package be.swsb.makeastats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.stubrunner.server.EnableStubRunnerServer;

@SpringBootApplication
@EnableStubRunnerServer
public class StubRunner {

    public static void main(String[] args) {
        SpringApplication.run(StubRunner.class, args);
    }
}
