package org.luxoft.interview;

import javax.annotation.PostConstruct;

import org.luxoft.interview.config.CacheConfig;
import org.luxoft.interview.util.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.luxoft.interview")
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @PostConstruct
    public void prepareData() {
    	ApplicationContext context = new AnnotationConfigApplicationContext(CacheConfig.class);
    	Data.prepareData();
    	((ConfigurableApplicationContext)context).close();
    }
}
