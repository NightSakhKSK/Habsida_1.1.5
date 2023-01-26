package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory factory = Util.getConnection();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            String sql =
                    "CREATE TABLE IF NOT EXISTS mydbtest.`users`" +
                    "(`ID` INT NOT NULL AUTO_INCREMENT," +
                    "`NAME` VARCHAR(50)," +
                    "`LAST_NAME` VARCHAR(50)," +
                    "`AGE` INTEGER," +
                    "PRIMARY KEY (`ID`))";
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS mydbtest.`users`";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (IllegalStateException e) {
            System.out.println("Таблица users не существует");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            User user = session.get(User.class, id);
            session.delete(user);
        } catch (PersistenceException e) {
            System.out.println("Таблица users не существует");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<User> user = session.createQuery("from User")
                    .getResultList();
            return user;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        } catch (
                PersistenceException e) {
            System.out.println(e);
        }
    }
}
