package ch.bbcag.javaee.db;

import ch.bbcag.javaee.UserSession;
import ch.bbcag.javaee.model.User;
import ch.bbcag.javaee.util.LogHelper;
import ch.bbcag.javaee.util.PasswordUtil;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;

@Named
@RequestScoped
public class UserDAO {

    private static final LogHelper logger = new LogHelper(UserDAO.class.getSimpleName());

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Resource
    private UserTransaction userTransaction;

    @Inject
    private UserSession userSession;

    /**
     * @return null if not logged in, otherwise the user
     */
    public User getCurrentUser() {
        if (!userSession.isLoggedIn()) {
            return null;
        }
        EntityManager manager = entityManagerFactory.createEntityManager();
        List<User> users = manager.createQuery("SELECT u FROM User u WHERE u.id= :id", User.class)
                                  .setParameter("id", userSession.getId()).getResultList();
        manager.close();
        return users.size() > 0 ? users.get(0) : null;
    }

    public User getUserByEmail(String email) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        List<User> users = manager.createQuery("SELECT u FROM User u WHERE u.email= :email", User.class)
                                  .setParameter("email", email).getResultList();
        manager.close();
        return users.size() > 0 ? users.get(0) : null;
    }

    public boolean exists(String email) {
        if (email == null) {
            return false;
        }
        User user = getUserByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean canLogIn(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        User user = getUserByEmail(email);
        return user != null && PasswordUtil.isValid(password, user.getPassword());
    }

    public void insertUser(User user) {
        EntityManager manager = null;
        try {
            userTransaction.begin();
            manager = entityManagerFactory.createEntityManager();
            manager.persist(user);
            userTransaction.commit();
        } catch (Exception e) {
            logger.loge("Failed to insert user", e);
            try {
                userTransaction.rollback();
            } catch (SystemException e1) {
                logger.loge("Failed to roll back transaction", e1);
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

}
