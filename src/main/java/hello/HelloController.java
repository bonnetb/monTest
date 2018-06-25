package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
@RestController
public class HelloController {
    @Value("#{systemProperties['qui'] ?: 'Inconnu'}")
    private String qui;
    
    @RequestMapping("/")
    public String index() {
        return "Bonjour " + qui + " !";
    }

}
