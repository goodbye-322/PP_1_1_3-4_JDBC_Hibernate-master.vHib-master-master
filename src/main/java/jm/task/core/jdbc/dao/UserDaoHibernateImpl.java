package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import static jm.task.core.jdbc.util.Util.getSessionFactory;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS user " +
                "(`id` bigint(19) NOT NULL AUTO_INCREMENT, " +
                "`name` varchar(45) NOT NULL, " +
                "`lastName` varchar(45) NOT NULL, " +
                "`age` tinyint(3) NOT NULL, " +
                "PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            session.createSQLQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);

        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> usersLists = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();
            usersLists = session.createQuery("from User", User.class).getResultList();
            transaction.commit();
            System.out.println(usersLists);
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return usersLists;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
