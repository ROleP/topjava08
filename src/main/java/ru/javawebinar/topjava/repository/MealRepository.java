package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * Created by rolep on 29/09/16.
 */
public interface MealRepository {
    void save(int userId, Meal meal);
    Meal delete(int userId, int id);
    void update(int userId, Meal meal);
    Meal getById(int userId, int id);
    Collection<Meal> getAll(int userId);
    void deleteUser(int userId);
}
