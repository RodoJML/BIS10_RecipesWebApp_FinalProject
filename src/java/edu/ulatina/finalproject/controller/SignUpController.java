/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.controller;
import edu.ulatina.finalproject.model.UserTO;
import edu.ulatina.finalproject.service.UserService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;


/**
 *
 * @author rodo
 */

@ManagedBean(name = "signUpController")
@SessionScoped

public class SignUpController implements Serializable{

    private UserTO newUser;
    
    public SignUpController() {

    }
    
    @PostConstruct
    public void createNew(){
        this.newUser = new UserTO(0,"","",0,"","");
    }
    
    public void signUp(){
        
        String  n   = this.newUser.getName();
        String  ln  = this.newUser.getLastName();
        int     a   = this.newUser.getAge();
        String  e   = this.newUser.getEmail();
        String  p   = this.newUser.getPassword();
        
        UserService userService = new UserService();
        userService.addUser(n, ln, a, e, p);
        
        FacesContext.getCurrentInstance().addMessage("sticky-key",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario " + n + " " + ln + " creado!" , e));
      
        this.newUser = new UserTO(0,"","",0,"","");
        
        PrimeFaces.current().ajax().update("signUpd");

    }
    
    /*public void reloadPage() {
        this.srv = new Service();
        this.srv.reDirect("/faces/index.xhtml");
    }*/

    public UserTO getNewUser() {
        return newUser;
    }

    public void setNewUser(UserTO newUser) {
        this.newUser = newUser;
    }
    
}
