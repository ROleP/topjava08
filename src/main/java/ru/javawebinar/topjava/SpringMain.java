package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

/**
 * Created by rolep on 10/31/16.
 */
public class SpringMain {

    public static void main(String[] args) {

        try(ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(applicationContext.getBeanDefinitionNames()));

            AdminRestController adminRestController = applicationContext.getBean(AdminRestController.class);
            adminRestController.create(new User(1, "username", "email", "password", Role.ROLE_ADMIN));
        }
    }
}
