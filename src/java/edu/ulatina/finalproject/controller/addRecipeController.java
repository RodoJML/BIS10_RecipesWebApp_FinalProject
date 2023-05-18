/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.controller;

import edu.ulatina.finalproject.model.IngredientTO;
import edu.ulatina.finalproject.model.RecipeTO;
import edu.ulatina.finalproject.service.IngredientService;
import edu.ulatina.finalproject.service.RecipeIngredientService;
import edu.ulatina.finalproject.service.RecipeService;
import edu.ulatina.finalproject.service.Service;
import edu.ulatina.finalproject.service.StepsService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;


@ManagedBean(name = "addRecipeController")
@ViewScoped

public class addRecipeController implements Serializable {

    private List<RecipeTO> recipes;
    private RecipeTO selectedRecipe;

    private List<IngredientTO> selectedIngredients;
    private List<IngredientTO> ingredients;

    private RecipeIngredientService recipeingredientService;
    private IngredientService ingredientService;
    private RecipeService recipeService;
    private StepsService stepsService;
    private Service accessService;

    private int stepsSize = 4;
    private int ingredientsCount = 2;
    
    private FileUploadView fileUploadView = new FileUploadView();

    public addRecipeController() {
        this.recipes = new ArrayList<>();

        this.recipeService = new RecipeService();
        this.accessService = new Service();
        this.stepsService = new StepsService();
        this.ingredientService = new IngredientService();
        this.recipeingredientService = new RecipeIngredientService();

        this.selectedRecipe = new RecipeTO();
    }

    @PostConstruct
    public void loadRecipes() {
        this.recipes = recipeService.list_with_Status();
        ingredients = ingredientService.list();
    }

    public void openNew() {
        this.selectedRecipe = new RecipeTO();
        this.selectedIngredients = new ArrayList<>();
        this.stepsSize = 4;
    }

    //Saves steps and ingredinets quantity 
    public void saveRecipeDetails() {
        
        System.out.println("ID de usuario que llega: " + this.selectedRecipe.getIdUser());

        for (int i = 0; i < this.selectedRecipe.getSteps().size(); i++) {

            String description = this.selectedRecipe.getSteps().get(i).getStepDescription();
            int idRecipe = this.selectedRecipe.getSteps().get(i).getIdRecipe();
            int idUser = this.selectedRecipe.getSteps().get(i).getIdUser();
            int stepNumber = this.selectedRecipe.getSteps().get(i).getStepNumner();

            this.stepsService.addStepsComplete(description, idRecipe, idUser, stepNumber);
        }

        for (int i = 0; i < this.selectedRecipe.getIngredients().size(); i++) {

            int idRecipe = this.selectedRecipe.getIngredients().get(i).getIdRecipe();
            int idUser = this.selectedRecipe.getIngredients().get(i).getIdUser();
            int idIngredient = this.selectedRecipe.getIngredients().get(i).getIdIngredient();
            int quantity = this.selectedRecipe.getIngredients().get(i).getQuantity();
            
            this.recipeingredientService.updateIngredientsRecipe(idRecipe, idUser, idIngredient, quantity);
        }

        PrimeFaces.current().ajax().update("formAllRecipes:allRecipes");
        PrimeFaces.current().executeScript("PF('addRecipeDetailsDialog').hide()");
        this.reloadPage();
    }

    // Create here how to add data to DB
    public void save() {

        
        this.recipeService.addRecipeInfo(this.selectedRecipe.getIdUser(),
                this.selectedRecipe.getName(),
                this.selectedRecipe.getDuration(),
                this.selectedRecipe.getDescription());

        PrimeFaces.current().executeScript("PF('addRecipeDialog').hide()");
        PrimeFaces.current().executeScript("PF('addRecipeDetailsDialog').show()");

        this.selectedRecipe.setIdRecipe(this.recipeService.getRecentRecipeID());

        this.reloadSteps();
        this.reloadIngredients();
        this.reloadSelectedRecipe();

        PrimeFaces.current().ajax().update("form:modalAddRecipeDetails:manage-recipeDetails-content");
    }

    public void reloadSelectedRecipe() {
        this.selectedRecipe = this.recipeService.pullSelectedRecipe(this.recipeService.getRecentRecipeID());
    }

    public void reloadSteps() {

        if (this.stepsSize < 4) {
            this.stepsSize = 4;
        }

        for (int i = 1; i <= this.stepsSize; i++) {
            this.stepsService.addStepsEmpty(this.selectedRecipe.getIdRecipe(), this.selectedRecipe.getIdUser(), i);
        }
    }

    public void reloadIngredients() {

        for (int i = 0; i < this.selectedIngredients.size(); i++) {

            String ingredientIDstr = "" + this.selectedIngredients.get(i);

            try {
                int recipeIDtonumber = Integer.parseInt(ingredientIDstr);
                this.recipeingredientService.addIngredientsRecipe(this.selectedRecipe.getIdRecipe(),
                        this.selectedRecipe.getIdUser(),
                        recipeIDtonumber, 0);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

        }

    }
    
    public void reloadPage() {
        this.accessService = new Service();
        this.accessService.reDirect("/faces/welcome.xhtml");
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

    public Service getAccessService() {
        return accessService;
    }

    public void setAccessService(Service accessService) {
        this.accessService = accessService;
    }

    public int getStepsSize() {
        return this.stepsSize;
    }

    public void setStepsSize(int stepsSize) {
        this.stepsSize = stepsSize;
    }

    public StepsService getStepsService() {
        return stepsService;
    }

    public void setStepsService(StepsService stepsService) {
        this.stepsService = stepsService;
    }

    public RecipeIngredientService getRecipeingredientService() {
        return recipeingredientService;
    }

    public void setRecipeingredientService(RecipeIngredientService recipeingredientService) {
        this.recipeingredientService = recipeingredientService;
    }

    public IngredientService getIngredientService() {
        return ingredientService;
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public int getIngredientsCount() {
        return ingredientsCount;
    }

    public void setIngredientsCount(int ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
    }

    public List<IngredientTO> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setSelectedIngredients(List<IngredientTO> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }

    public List<IngredientTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientTO> ingredients) {
        this.ingredients = ingredients;
    }

    public FileUploadView getFileUploadView() {
        return fileUploadView;
    }

    public void setFileUploadView(FileUploadView fileUploadView) {
        this.fileUploadView = fileUploadView;
    }
    
    

}
