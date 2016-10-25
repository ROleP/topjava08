package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.util.List;

/**
 * Created by rolep on 19/10/16.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public void save(int userId, Meal meal) {
        repository.save(userId, meal);
    }

    @Override
    public void delete(int userId, int id) {
        ExceptionUtil.checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public void update(int userId, Meal meal) {
        repository.update(userId, meal);
    }

    @Override
    public Meal getById(int userId, int id) {
        return ExceptionUtil.checkNotFoundWithId(repository.getById(userId, id), id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    @Override
    public void deleteUser(int userId) {
        repository.deleteUser(userId);
    }
}
