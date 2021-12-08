package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;

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
    public void save(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User findUserByEmail(String email) {
        String query = String.format("FROM User U WHERE U.email = '%s'", email);
        return (User) tx(session -> {
            List list = session.createQuery(query).list();
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        });
    }

    @Override
    public User findUserByName(String name) {
        String query = String.format("FROM User U WHERE U.name = '%s'", name);
        return (User) tx(session -> {
            List list = session.createQuery(query).list();
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        });
    }

    @Override
    public Item findById(int id) {
        return tx(session -> session.get(Item.class, id));
    }

    @Override
    public boolean delete(int id) {
        if (findById(id) == null) {
            return false;
        }
        return tx(session -> {
            Item item = session.get(Item.class, id);
            session.delete(item);
            return true;
        });
    }

    @Override
    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item").list());
    }

    private <T> T tx(final Function<Session, T> command) {
        Session session = sf.openSession();
        try {
            T rsl = command.apply(session);
            session.beginTransaction().commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
