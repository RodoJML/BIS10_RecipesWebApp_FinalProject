/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.model;

import edu.ulatina.finalproject.service.UserService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author rodo
 */
public class RecipeTO implements Serializable {

    private int idRecipe;    // Must auto Increment
    private int idUser;
    private String name;
    private String userName;
    private int duration;
    private String description;
    private int status; // ******
    private String statusText;
    private boolean status1;
    private String fileName;
    
    private List<RecipeIngredientsTO> ingredients;
    private List<StepsTO> steps;

    private UserService us = new UserService();
    
    public RecipeTO() {
            
    }

    public RecipeTO(int idRecipe, int idUser, String name, int duration, String description, List<RecipeIngredientsTO> ingredients, List<StepsTO> steps, int status, String fileName) {
        this.idRecipe = idRecipe;
        this.idUser = idUser;
        this.name = name;
        this.duration = duration;
        this.description = description; 
        this.ingredients = ingredients;
        this.steps = steps;
        this.userName = us.getUserName(idUser);
        this.status = status;
        this.fileName = fileName;
        
        if(status == 1){
            this.statusText = "Activa";
            this.status1 = true;
        }else{
            this.statusText = "Oculta";
            this.status1 = false;
        }
            
    }
    
    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<RecipeIngredientsTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientsTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepsTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsTO> steps) {
        this.steps = steps;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserService getUs() {
        return us;
    }

    public void setUs(UserService us) {
        this.us = us;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public boolean isStatus1() {
        return status1;
    }

    public void setStatus1(boolean status1) {
        
        if(status1)
            this.status = 1;
        else
            this.status = 0;
        
        this.status1 = status1;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    
    
}
