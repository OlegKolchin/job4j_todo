package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {
    public void save(Item item);

    public Item findById(int id);

    public boolean delete(int id);

    public List<Item> findAll();


}
