package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rolep on 29/09/16.
 */
public class InMemoryMealRepositoryImpl implements MealRepository {

    private final static Map<Integer, Meal> MEALS = new ConcurrentHashMap<>();

    {
        MealsUtil.MEAL_LIST.forEach(this::save);
    }

    private static AtomicInteger counter = new AtomicInteger(1000);

    @Override
    public void save(Meal meal) {
        if (meal.isNew) {
            meal.setId(counter.incrementAndGet());
        }
        MEALS.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        MEALS.remove(id);
    }

    @Override
    public void update(Meal meal) {
        save(meal);
    }

    @Override
    public Meal getById(int id) {
        return MEALS.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return MEALS.values();
    }
}
