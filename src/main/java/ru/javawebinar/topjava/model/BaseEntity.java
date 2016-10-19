package ru.javawebinar.topjava.model;

/**
 * Created by rolep on 19/10/16.
 */
public class BaseEntity {

    protected Integer id;

    public BaseEntity() {}

    protected BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
