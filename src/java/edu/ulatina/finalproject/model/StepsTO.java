/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.model;

import java.io.Serializable;

/**
 *
 * @author rodo
 */
public class StepsTO implements Serializable {
    
    private int idStep;
    private int idRecipe;
    private int idUser;
    private int stepNumner;
    private String stepDescription;

    public StepsTO() {
    }

    public StepsTO(int idStep, int idRecipe, int idUser, int stepNumner, String stepDescription) {
        this.idStep = idStep;
        this.idRecipe = idRecipe;
        this.idUser = idUser;
        this.stepNumner = stepNumner;
        this.stepDescription = stepDescription;
    }

    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getStepNumner() {
        return stepNumner;
    }

    public void setStepNumner(int stepNumner) {
        this.stepNumner = stepNumner;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    
}
