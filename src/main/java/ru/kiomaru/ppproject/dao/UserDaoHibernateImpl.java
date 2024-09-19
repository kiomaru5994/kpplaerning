package ru.kiomaru.ppproject.dao;

import org.hibernate.Session;
import ru.kiomaru.ppproject.model.User;
import ru.kiomaru.ppproject.util.Util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Util util = new Util();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = util.getSession();
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS `pp_schema`.`Users` (" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(15) NOT NULL," +
                "  `lastName` VARCHAR(15) NOT NULL," +
                "  `age` TINYINT NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        try {
            session.beginTransaction();
            session.createNativeQuery(createUserTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            logger.warning("Error creating user table: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.getSession();
        String dropUserTableSQL = "DROP TABLE IF EXISTS `pp_schema`.`Users`;";
        try {
            session.beginTransaction();
            session.createNativeQuery(dropUserTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            logger.warning("Error dropping user table: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = util.getSession();
        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            logger.warning("Error saving user: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = util.getSession();
        try {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            logger.warning("Error removing user: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = util.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.from(User.class);
        List<User> users = session.createQuery(query).getResultList();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = util.getSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
