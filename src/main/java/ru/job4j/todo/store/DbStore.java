package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;

public class DbStore implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void save(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.saveOrUpdate(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean delete(int id) {
        if (findById(id) == null) {
            return false;
        }
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List rsl = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }
}
