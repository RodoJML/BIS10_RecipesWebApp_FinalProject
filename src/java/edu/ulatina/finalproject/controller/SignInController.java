
 /* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// This is the first class created, while explaining professor said...
// "We are going to start from the "clase negocio" all the way to the view class"

package edu.ulatina.finalproject.controller;
// Since this app will be oriented do an education institution we create the 
// package as "edu" and the institution is "Ulatina" this should be an standard 
// for package creation.

import edu.ulatina.finalproject.model.UserTO;
import edu.ulatina.finalproject.service.UserService;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Rodolfo Meneses Leal
 */

@ManagedBean(name = "signInController")

@SessionScoped

public class SignInController implements Serializable {
    
    private String email;
    private String password;
    private UserTO userTO = new UserTO();
    private List<UserTO> userList = new ArrayList<UserTO>();

    // Constructors
    public SignInController() {
    }

    public SignInController(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Methods
    public void login() {
        
        UserService userService = new UserService();
        
        this.userTO = userService.validate(this.email, this.password);

        if (this.userTO != null) {

            this.reDirect("/faces/welcome.xhtml");
            
            System.out.println("Correct credentials");
            
        } else {
            
            FacesContext.getCurrentInstance().addMessage("sticky-key", 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect credentials", "Username or password incorrect"));
           
            System.out.println("Incorrect credentials");
        }
        
        this.email = "";
        this.password = "";
    }
    
    
    public void reDirect(String url){
        
        HttpServletRequest request;
        // Http standard has 2 communication ways, "request" and "respond"
        // This method will handle the "request" piece
        
        try{
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + url);
        } catch(Exception e){
            
        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public UserTO getUserTO() {
        return userTO;
    }

    public void setUserTO(UserTO userTO) {
        this.userTO = userTO;
    }

    public List<UserTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserTO> userList) {
        this.userList = userList;
    }
    
    // Getters and setters are critical on this controller, otherwise attributes
    // from this class won't be able accesible from the view "files.html"
    
}
