package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


import static org.slf4j.LoggerFactory.*;

/**
 * Created by rolep on 29/09/16.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private InMemoryMealRepositoryImpl repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text / html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        if (action.equals("edit") || action.equals("add")) {
            int id = getId(req);
            final Meal meal = action.equals("add")
                    ? new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", MealsUtil.DEFAULT_CALORIES_PER_DAY)
                    : repository.getById(id);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
        }
        else {

            if (action.equals("delete")) {
                int id = getId(req);
                if (id > 0) {
                    repository.delete(id);
                }
            }

            LocalDate startDate = LocalDate.now().withDayOfMonth(1).withMonth(1);
            LocalDate endDate = LocalDate.now();
            LocalTime startTime = LocalTime.MIN;
            LocalTime endTime = LocalTime.MAX;
            int caloriesLimit = MealsUtil.DEFAULT_CALORIES_PER_DAY;
            String s;

            if ((s = req.getParameter("sd")) != null) {
                try {
                    startDate = LocalDate.parse(s);
                } catch (DateTimeParseException e) {
                    LOG.info("wrong start date provided: {}", s);
                }
            }
            if ((s = req.getParameter("ed")) != null) {
                try {
                    endDate = LocalDate.parse(s);
                } catch (DateTimeParseException e) {
                    LOG.info("wrong end date provided: {}", s);
                }
            }
            if ((s = req.getParameter("st")) != null) {
                try {
                    startTime = LocalTime.parse(s);
                } catch (DateTimeParseException e) {
                    LOG.info("wrong start time provided: {}", s);
                }
            }
            if ((s = req.getParameter("et")) != null) {
                try {
                    endTime = LocalTime.parse(s);
                } catch (DateTimeParseException e) {
                    LOG.info("wrong end time provided: {}", s);
                }
            }
            if ((s = req.getParameter("cal")) != null) {
                try {
                    caloriesLimit = Integer.valueOf(s);
                } catch (NumberFormatException e) {
                    LOG.info("wrong calories limit provided: {}", s);
                }
            }

            List<MealWithExceed> meals = MealsUtil.getFilteredWithExceededFF(repository.getAll(), startDate, endDate, startTime, endTime, caloriesLimit);

            req.setAttribute("meals", meals);
            req.setAttribute("startDate", startDate);
            req.setAttribute("endDate", endDate);
            req.setAttribute("startTime", TimeUtil.formatLocalTime(startTime, "HH:mm:ss"));
            req.setAttribute("endTime", TimeUtil.formatLocalTime(endTime, "HH:mm:ss"));
            req.setAttribute("calories", caloriesLimit);

            LOG.debug("forward to mealList");
            req.getRequestDispatcher("/mealList.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text / html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        int id = getId(req);

        Meal meal = new Meal(id > 0 ? id : 0,
                LocalDateTime.parse(req.getParameter("dt"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                req.getParameter("desc"),
                Integer.valueOf(req.getParameter("cal")));
        LOG.info(meal.isNew ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        resp.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId;
        int result = 0;
        if ((paramId = request.getParameter("id")) != null) {
            try {
                result = Integer.valueOf(paramId);
            } catch (NumberFormatException e) {}
        }
        return result;
    }
}
