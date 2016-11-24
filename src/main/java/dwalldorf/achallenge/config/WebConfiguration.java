package dwalldorf.achallenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebConfiguration extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
        logger.info("globally enabled CORS");
    }
}
