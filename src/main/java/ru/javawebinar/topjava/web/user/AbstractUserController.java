package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

/**
 * Created by rolep on 19/10/16.
 */
public abstract class AbstractUserController {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    public List<User> getAll() {
        LOG.info("get all");
        return service.getAll();
    }

    public User get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return service.getByEmail(email);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
    }

    public void update(User user, int id) {
        user.setId(id);
        LOG.info("update " + user);
        service.update(user);
    }

    public User create(User user) {
        user.setId(null);
        LOG.info("create " + user);
        return service.save(user);
    }
}
