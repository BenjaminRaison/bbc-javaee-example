package ch.bbcag.javaee.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
@SessionScoped
@URLMapping(id = "register", pattern = "/example/register/", viewId = "/example/register.jsf")
public class ControllerRegister implements Serializable {


    public String getText() {
        return "Hello World";
    }
}
