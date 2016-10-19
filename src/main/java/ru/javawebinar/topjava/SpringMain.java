package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Arrays;

/**
 * Created by rolep on 19/10/16.
 */
public class SpringMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(applicationContext.getBeanDefinitionNames()));

//        UserRepository userRepository = (UserRepository) applicationContext.getBean("mockUserRepository");
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        userRepository.getAll();
        applicationContext.close();
    }
}
