package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {
    public void save(Item item);

    public void save(User user);

    public Item findById(int id);

    public User findUserByEmail(String email);

    public boolean delete(int id);

    public List<Item> findAll();


}
