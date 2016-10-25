package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

/**
 * Created by rolep on 19/10/16.
 */
@Controller
public class MealRestController {

    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public void save(Meal meal) {
        LOG.info("user " + AuthorizedUser.id() + ", save meal " + meal);
        service.save(AuthorizedUser.id(), meal);
    }

    public void delete(int id) {
        LOG.info("user " + AuthorizedUser.id() + ", delete meal " + id);
        service.delete(AuthorizedUser.id(), id);
    }

    public void update(Meal meal) {
        LOG.info("user " + AuthorizedUser.id() + ", update meal " + meal);
        service.update(AuthorizedUser.id(), meal);
    }

    public Meal getById(int id) {
        LOG.info("user " + AuthorizedUser.id() + ", getById " + id);
        return service.getById(AuthorizedUser.id(), id);
    }

    public List<Meal> getAll() {
        LOG.info("user " + AuthorizedUser.id() + ", getAll");
        return service.getAll(AuthorizedUser.id());
    }
}
