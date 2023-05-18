/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.controller;

import edu.ulatina.finalproject.model.RecipeTO;
import edu.ulatina.finalproject.service.RecipeIngredientService;
import edu.ulatina.finalproject.service.RecipeService;
import edu.ulatina.finalproject.service.StepsService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;


/**
 *
 * @author rodo
 */
@ManagedBean (name = "dataGridView")
@ViewScoped

public class DataGridView implements Serializable{

    private List<RecipeTO> recipes = new ArrayList<>();;
    private RecipeTO selectedRecipe = new RecipeTO();
    
    private RecipeService recipeService = new RecipeService();
    private RecipeIngredientService rIngredientService = new RecipeIngredientService();
    private StepsService stepsService = new StepsService();
    
    private FileUploadView fileUploadView = new FileUploadView();

    public DataGridView() {
    }
    
    @PostConstruct
    public void init() {
        this.recipes = recipeService.list_with_Status();
    }

    public void displayRecipe(){
        
    }
    
    public void saveEdits(){
        
        int idRecipe = this.selectedRecipe.getIdRecipe();
        int idUser = this.selectedRecipe.getIdUser();
        String name = this.selectedRecipe.getName();
        int duration = this.selectedRecipe.getDuration();
        String description = this.selectedRecipe.getDescription();
        
        if(this.fileUploadView.isEventHappened()){
            String fileName = this.fileUploadView.getFile().getFileName();
            this.recipeService.addFileName(idRecipe, fileName);
        }    
        
        this.recipeService.updateRecipeInfo(idRecipe, idUser, name, duration, description, 1);
        
        for(int i = 0; i < this.selectedRecipe.getSteps().size(); i++){
            int stepNumber = this.selectedRecipe.getSteps().get(i).getStepNumner();
            String stepdescription = this.selectedRecipe.getSteps().get(i).getStepDescription();
            
            this.stepsService.addStepsComplete(stepdescription, idRecipe, idUser, stepNumber);
        }
        
        for(int i = 0; i < this.selectedRecipe.getIngredients().size(); i++){
            int ingredientID = this.selectedRecipe.getIngredients().get(i).getIdIngredient();
            int quantity = this.selectedRecipe.getIngredients().get(i).getQuantity();
            
            this.rIngredientService.updateIngredientsRecipe(idRecipe, idUser, ingredientID, quantity);
        }
        
        this.recipes = recipeService.list_with_Status();
        PrimeFaces.current().ajax().update("formAllRecipes:allRecipes");
        PrimeFaces.current().executeScript("PF('editRecipeDialog').hide()");
    }
    
    
    public List<RecipeTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeTO> recipes) {
        this.recipes = recipes;
    }

    public RecipeTO getSelectedRecipe() {
        return selectedRecipe;
    }

    public void setSelectedRecipe(RecipeTO selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    public RecipeService getRecipeService() {
        return recipeService;
    }

    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public StepsService getStepsService() {
        return stepsService;
    }

    public void setStepsService(StepsService stepsService) {
        this.stepsService = stepsService;
    }

    public RecipeIngredientService getrIngredientService() {
        return rIngredientService;
    }

    public void setrIngredientService(RecipeIngredientService rIngredientService) {
        this.rIngredientService = rIngredientService;
    }

    public FileUploadView getFileUploadView() {
        return fileUploadView;
    }

    public void setFileUploadView(FileUploadView fileUploadView) {
        this.fileUploadView = fileUploadView;
    }
    
    
    
}

  