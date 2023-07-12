package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static jm.task.core.jdbc.util.Util.getSessionFactory;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;
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
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.createSQLQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null){
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersLists = new ArrayList<>();

        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            usersLists = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
            System.out.println(usersLists);
        } catch (Exception e) {
            if (session != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return usersLists;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}
