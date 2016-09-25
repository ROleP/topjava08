package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

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
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        List<UserMealWithExceed> filteredMealsWithExceed = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredMealsWithExceed.forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesPerDay = mealList.stream()
                .collect(
                        Collectors.groupingBy(m -> m.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> createWithExceed(m, totalCaloriesPerDay.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMealWithExceed>> mealsByDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            if (!mealsByDay.containsKey(userMeal.getDateTime().toLocalDate())) {
                mealsByDay.put(userMeal.getDateTime().toLocalDate(), new ArrayList<>());
            }
            mealsByDay.get(userMeal.getDateTime().toLocalDate())
                    .add(new UserMealWithExceed(userMeal, false));
        }
        for (List<UserMealWithExceed> userMealWithExceeds : mealsByDay.values()) {
            int sum = 0;
            for (UserMealWithExceed mealWithExceed : userMealWithExceeds) {
                sum += mealWithExceed.getCalories();
            }
            if (sum > caloriesPerDay) {
                for (UserMealWithExceed mealWithExceed : userMealWithExceeds) {
                    mealWithExceed.setExceed(true);
                }
            }
        }
        List<UserMealWithExceed> result = new ArrayList<>();
        for (List<UserMealWithExceed> userMealWithExceeds : mealsByDay.values()) {
            for (UserMealWithExceed mealWithExceed : userMealWithExceeds) {
                if (TimeUtil.isBetween(mealWithExceed.getDateTime().toLocalTime(), startTime, endTime)) {
                    result.add(mealWithExceed);
                }
            }
        }
        return result;
    }

    public static UserMealWithExceed createWithExceed(UserMeal um, boolean exceed) {
        return new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), exceed);
    }
}
