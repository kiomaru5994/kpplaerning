package ru.kiomaru.ppproject.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.kiomaru.ppproject.model.User;
import ru.kiomaru.ppproject.util.Util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Util util = new Util();
    private final SessionFactory sessionFactory = util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS `pp_schema`.`Users` (" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(15) NOT NULL," +
                "  `lastName` VARCHAR(15) NOT NULL," +
                "  `age` TINYINT NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createNativeQuery(createUserTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.warning("Error creating user table: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String dropUserTableSQL = "DROP TABLE IF EXISTS `pp_schema`.`Users`;";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createNativeQuery(dropUserTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.warning("Error dropping user table: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction!= null) transaction.rollback();
            logger.warning("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.warning("Error removing user: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            query.from(User.class);
            users = session.createQuery(query).getResultList();
        } catch (Exception e) {
            logger.warning("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }
}
