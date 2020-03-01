package pl.camp.it.configuration;


import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.hibernate.cfg.Configuration;

@org.springframework.context.annotation.Configuration
public class AppConfiguration {
    @Bean
    public SessionFactory sessionFactory(){
        return new Configuration().configure().buildSessionFactory();
    }
}
