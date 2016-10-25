package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by rolep on 19/10/16.
 */
public interface MealService {

    void save(int userId, Meal meal);
    void delete(int userId, int id);
    void update(int userId, Meal meal);
    Meal getById(int userId, int id);
    List<Meal> getAll(int userId);
    void deleteUser(int userId);
}
