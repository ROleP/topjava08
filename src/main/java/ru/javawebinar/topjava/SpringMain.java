package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

/**
 * Created by rolep on 19/10/16.
 */
public class SpringMain {

    public static void main(String[] args) {
//        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        try(ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(applicationContext.getBeanDefinitionNames()));
            AdminRestController adminRestController = applicationContext.getBean(AdminRestController.class);
            adminRestController.create(new User(1, "Tester", "tester@example.com", "password", Role.ROLE_ADMIN));

//        UserRepository userRepository = (UserRepository) applicationContext.getBean("mockUserRepository");
//            UserRepository userRepository = applicationContext.getBean(UserRepository.class);
//            userRepository.getAll();
//
//            UserService userService = applicationContext.getBean(UserService.class);
//            userService.save(new User(1, "Tester", "tester@example.com", "password", Role.ROLE_USER));
//        applicationContext.close();
        }
    }
}
