package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * Created by rolep on 19/10/16.
 */
@Controller
public class AdminRestController extends AbstractUserController {

    public List<User> getAll() {
        return super.getAll();
    }

    public User get(int id) {
        return super.get(id);
    }

    public User create(User user) {
        return super.create(user);
    }

    public void delete(int id) {
        super.delete(id);
    }

    public User getByEmail(String email) {
        return super.getByEmail(email);
    }

    public void update(User user, int id) {
        super.update(user, id);
    }
}
