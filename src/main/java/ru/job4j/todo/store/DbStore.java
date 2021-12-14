package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public void save(Category category) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.saveOrUpdate(category);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User findUserByEmail(String email) {
        String query = String.format("FROM User U WHERE U.email = '%s'", email);
        return (User) tx(session -> session.createQuery(query).uniqueResult());
    }

    @Override
    public User findUserByName(String name) {
        String query = String.format("FROM User U WHERE U.name = '%s'", name);
        return (User) tx(session -> session.createQuery(query).uniqueResult());
    }

    @Override
    public Item findById(int id) {
        return tx(session -> session.get(Item.class, id));
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery("delete from Item where id = " + id).executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public void updateItem(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createSQLQuery("update items set done = not(done) where id=" + id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
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

    @Override
    public List<Category> findAllCategories() {
        return tx(session -> session.createQuery("from Category").list());
    }

    public Category findCategory(int id) {
        return tx(session -> session.get(Category.class, id));
    }

    public HashSet<Category> parseCategories(String param, DbStore store) {
        String[] rsl = param.split("");
        List<Integer> list = Arrays.stream(rsl).map(Integer::parseInt).collect(Collectors.toList());
        HashSet<Category> categories = new HashSet<>();
        for (int i : list) {
            categories.add(store.findCategory(i));
        }
        return categories;
    }

    public static void main(String[] args) {
        Item item = new DbStore().findById(3);
        System.out.println(item.getCreated());

    }

}
