package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {
    public void save(Item item, String categories);

    public void save(User user);

    public void save(Category category);

    public void updateItem(int id);

    public Item findById(int id);

    public User findUserByEmail(String email);

    public User findUserByName(String name);

    public boolean delete(int id);

    public List<Item> findAll();

    public List<Category> findAllCategories();


}
