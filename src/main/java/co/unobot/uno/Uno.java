package co.unobot.uno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Created by shyam on 30/03/17.
 */
@SpringBootApplication
public class Uno extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Uno.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Uno.class, args);
    }
}
