package co.unobot.uno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by shyam on 30/03/17.
 */
@SpringBootApplication
public class Uno {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Uno.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(Uno.class, args);
    }
}
