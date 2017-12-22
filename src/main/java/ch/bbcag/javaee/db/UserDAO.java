package ch.bbcag.javaee.db;

import ch.bbcag.javaee.UserSession;
import ch.bbcag.javaee.model.User;
import ch.bbcag.javaee.util.PasswordUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Named
@RequestScoped
public class UserDAO {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

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
        User user = manager.createQuery("SELECT u FROM User u WHERE u.id= :id", User.class)
                           .setParameter("id", userSession.getId()).getSingleResult();
        manager.close();
        return user;
    }

    public User getUserByEmail(String email) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        User user = manager.createQuery("SELECT u FROM User u WHERE u.email= :email", User.class)
                           .setParameter("email", email).getSingleResult();
        manager.close();
        return user;
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

}
