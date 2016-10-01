package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * Created by rolep on 29/09/16.
 */
public interface MealRepository {
    void save(Meal meal);
    void delete(int id);
    void update(Meal meal);
    Meal getById(int id);
    Collection<Meal> getAll();
}
