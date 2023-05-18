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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author rodo
 */
@ManagedBean(name = "crudRecipesController")
@ViewScoped

public class CrudRecipesController {

    private List<RecipeTO> recipes = new ArrayList<>();
    private List<RecipeTO> selectedRecipes = new ArrayList<>();
    private RecipeTO selectedRecipe = new RecipeTO();

    private List<IngredientTO> ingredients = new ArrayList<>();
    private List<IngredientTO> selectedIngredients = new ArrayList<>();
    private IngredientTO selectedIngredient = new IngredientTO();

    private Service accessService;
    private RecipeService rService;
    private RecipeIngredientService riService;
    private IngredientService iService;
    private StepsService stepsService;

    private int stepsSize = 4;

    @PostConstruct
    public void init() {
        this.rService = new RecipeService();
        this.iService = new IngredientService();
        this.riService = new RecipeIngredientService();
        this.recipes = this.rService.list();
        this.ingredients = this.iService.list();
        this.stepsService = new StepsService();

    }

    public void openPage() {
        this.accessService = new Service();
        this.accessService.reDirect("/faces/recipesMaintenance.xhtml");
    }

    public void setStatus(RecipeTO recipeStat) {

        int idRp = recipeStat.getIdRecipe();
        int idUsr = recipeStat.getIdUser();
        int stat = recipeStat.getStatus();

        System.out.println("receta: " + idRp);
        System.out.println("usuario: " + idUsr);
        System.out.println("status que llega: " + stat);

        this.rService.updateStatus(idRp, idUsr, stat);
    }

    public void loadIngredients(RecipeTO recipe) {

        List<IngredientTO> ingredients1 = new ArrayList<>();

        for (int i = 0; i < recipe.getIngredients().size(); i++) {

            int idIngredient = recipe.getIngredients().get(i).getIdIngredient();
            String IngredientName = this.iService.getIngredientName(idIngredient);

            IngredientTO ingredient = new IngredientTO(idIngredient, IngredientName);
            ingredients1.add(ingredient);
        }

        this.selectedRecipe = recipe;
        this.stepsSize = recipe.getSteps().size();
        this.selectedIngredients = ingredients1;
    }

    public void reloadSteps() {

        if (this.stepsSize < 4) {
            this.stepsSize = 4;
            
            for (int i = 1; i <= this.stepsSize; i++) {
                this.stepsService.addStepsEmpty(this.selectedRecipe.getIdRecipe(), this.selectedRecipe.getIdUser(), i);
            }
            
            return;
        } else if (this.selectedRecipe.getSteps().size() == this.stepsSize) {
            return;
        } else {
            System.out.println("ID que llega: " + this.selectedRecipe.getIdRecipe());
            this.stepsService.clearSteps(this.selectedRecipe.getIdRecipe());

            for (int i = 1; i <= this.stepsSize; i++) {
                this.stepsService.addStepsEmpty(this.selectedRecipe.getIdRecipe(), this.selectedRecipe.getIdUser(), i);
            }
        }

    }

    public void reloadIngredients() {
        
        this.riService.clearIngredients(this.selectedRecipe.getIdRecipe());

        for (int i = 0; i < this.selectedIngredients.size(); i++) {

            String ingredientIDstr = "" + this.selectedIngredients.get(i);

            try {
                int recipeIDtonumber = Integer.parseInt(ingredientIDstr);
                this.riService.addIngredientsRecipe(this.selectedRecipe.getIdRecipe(),
                        this.selectedRecipe.getIdUser(),
                        recipeIDtonumber, 0);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

        }

    }

    public void saveIngredients() {
        this.reloadIngredients();
        this.recipes = this.rService.list();
        PrimeFaces.current().ajax().update("form:dt-products");
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ingredientes Actualizados", "Ingredientes en receta: " + this.selectedIngredients.size()));
        PrimeFaces.current().ajax().update("dialogs:messagesUpdates");
    }
    
    
    public void saveSteps() {
        this.reloadSteps();
        this.recipes = this.rService.list();
        PrimeFaces.current().ajax().update("form:dt-products");
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pasos Actualizados", "Pasos en receta: " + this.stepsSize));
        PrimeFaces.current().ajax().update("dialogs:messagesUpdates");
    }
    
    

    public List<RecipeTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeTO> recipes) {
        this.recipes = recipes;
    }

    public List<RecipeTO> getSelectedRecipes() {
        return selectedRecipes;
    }

    public void setSelectedRecipes(List<RecipeTO> selectedRecipes) {
        this.selectedRecipes = selectedRecipes;
    }

    public RecipeTO getSelectedRecipe() {
        return selectedRecipe;
    }

    public void setSelectedRecipe(RecipeTO selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    public Service getAccessService() {
        return accessService;
    }

    public void setAccessService(Service accessService) {
        this.accessService = accessService;
    }

    public RecipeService getrService() {
        return rService;
    }

    public void setrService(RecipeService rService) {
        this.rService = rService;
    }

    public List<IngredientTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredientTO> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setSelectedIngredients(List<IngredientTO> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }

    public IngredientTO getSelectedIngredient() {
        return selectedIngredient;
    }

    public void setSelectedIngredient(IngredientTO selectedIngredient) {
        this.selectedIngredient = selectedIngredient;
    }

    public IngredientService getiService() {
        return iService;
    }

    public void setiService(IngredientService iService) {
        this.iService = iService;
    }

    public RecipeIngredientService getRiService() {
        return riService;
    }

    public void setRiService(RecipeIngredientService riService) {
        this.riService = riService;
    }

    public int getStepsSize() {
        return stepsSize;
    }

    public void setStepsSize(int stepsSize) {
        this.stepsSize = stepsSize;
    }

}
