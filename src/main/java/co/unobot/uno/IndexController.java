package co.unobot.uno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shyam on 14/04/17.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
