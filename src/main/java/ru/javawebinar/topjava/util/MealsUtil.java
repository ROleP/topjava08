package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEAL_LIST = Arrays.asList(
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<MealWithExceed> filteredMealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY);
        filteredMealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }

    public static List<MealWithExceed> getFilteredWithExceeded() {
        return getFilteredWithExceeded(LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return getFilteredWithExceeded(MEAL_LIST, startTime, endTime, caloriesPerDay);
    }

    public static List<MealWithExceed> getFilteredWithExceededFF(Collection<Meal> meals, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<Meal> selectedDaysMeals = meals.stream()
                .filter(meal -> TimeUtil.isBetweenDate(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());

        Map<LocalDate, Integer> caloriesSumByDate = selectedDaysMeals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return selectedDaysMeals.stream()
                .filter(meal -> TimeUtil.isBetweenTime(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenTime(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenTime(meal.getTime(), startTime, endTime)) {
                mealExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}