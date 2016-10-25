package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by rolep on 19/10/16.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private final static Map<Integer, User> USERS = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(counter.incrementAndGet());
        }
        LOG.info("save " + user);
        return USERS.put(user.getId(), user);
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return USERS.remove(id) != null;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return USERS.get(id);
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        for (User user : USERS.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return USERS.values().stream().sorted((u1, u2) -> u1.getName().compareTo(u2.getName())).collect(Collectors.toList());
    }
}
