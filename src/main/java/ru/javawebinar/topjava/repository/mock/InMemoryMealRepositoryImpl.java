package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by rolep on 29/09/16.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private final static Map<Integer, Map<Integer, Meal>> MEALS = new ConcurrentHashMap<>();

    {
        MealsUtil.MEAL_LIST.forEach((meal) -> save(1, meal));
    }

    private static AtomicInteger counter = new AtomicInteger(1000);

    @Override
    public void save(int userId, Meal meal) {
        if (meal.isNew) {
            meal.setId(counter.incrementAndGet());
        }
        MEALS.putIfAbsent(userId, new ConcurrentHashMap<>());
        MEALS.get(userId).put(meal.getId(), meal);
    }

    @Override
    public Meal delete(int userId, int id) {
        return MEALS.get(userId).remove(id);
    }

    @Override
    public void update(int userId, Meal meal) {
        save(userId, meal);
    }

    @Override
    public Meal getById(int userId, int id) {
        return MEALS.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return MEALS.get(userId).values().stream().sorted((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime())).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int userId) {
        MEALS.remove(userId);
    }
}
