package ch.bbcag.javaee.controller;

import ch.bbcag.javaee.model.Item;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.*;
import java.io.Serializable;

@ManagedBean
@SessionScoped
@URLMapping(id = "register", pattern = "/register/", viewId = "/register.jsf")
public class ControllerRegister implements Serializable {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction ut;

    public String getText() {
        try {
            ut.begin();
            EntityManager manager = emf.createEntityManager();
            Item item = new Item();
            item.setTitle("Hi");
            item.setPrice(25d);
            item.setDescription("Blablabla");
            manager.persist(item);
            ut.commit();
        } catch (RollbackException | HeuristicMixedException | SystemException | HeuristicRollbackException | NotSupportedException e) {
            e.printStackTrace();
        }
        return "Hello World";
    }
}
