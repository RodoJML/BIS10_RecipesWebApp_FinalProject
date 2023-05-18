/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.controller;

import edu.ulatina.finalproject.model.IngredientTO;
import edu.ulatina.finalproject.service.IngredientService;
import edu.ulatina.finalproject.service.RecipeIngredientService;
import edu.ulatina.finalproject.service.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@ManagedBean(name = "crudIngredientController")
@SessionScoped

public class CrudIngredientsController implements Serializable {

    private List<IngredientTO> ingredientsInventory;
    private IngredientTO selectedIngredient;
    private List<IngredientTO> selectedIngredients;
    private IngredientService ingredientService;
    private RecipeIngredientService riService;
    private Service accessService;

    public CrudIngredientsController() {
        this.ingredientsInventory = new ArrayList<>();
        this.selectedIngredients = new ArrayList<>();
        this.ingredientService = new IngredientService();
        this.accessService = new Service();
        this.riService = new RecipeIngredientService();
    }

    @PostConstruct
    public void loadIngredients() {
        this.ingredientsInventory = ingredientService.list();
    }

    public void openNew() {
        this.selectedIngredient = new IngredientTO();
    }

    public void saveProduct(int idUser) {

        // Enter service and deletes from DB
        ingredientService.addIngredient(this.selectedIngredient.getIngredientName(), idUser);

        // Loads all ingredients again so it can be refreshed on the platform
        this.loadIngredients();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ingrediente Agregado"));
        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void deleteProduct(int idUser) {

        // Enter service and deletes from DB
        ingredientService.deleteIngredient(this.selectedIngredient.getIdIngredient(), idUser);
        this.riService.clearSingleIngredient(this.selectedIngredient.getIdIngredient());

        // Removes the objects from code so it can be updated on the view once product is deleted 
        this.ingredientsInventory.remove(this.selectedIngredient);
        this.selectedIngredients.remove(this.selectedIngredient);
        this.selectedIngredient = null;

        FacesContext.getCurrentInstance().addMessage("sticky-key",
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrediente Eliminado", ""));

        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    
    
    public void deleteAllIngredients(int idUser) {
        
        ingredientService.deleteAllIngredients(idUser);
        
        this.loadIngredients();
  
        FacesContext.getCurrentInstance().addMessage("sticky-key",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Todos los ingredientes eliminados!", ""));

        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    
    
    public String getDeleteButtonMessage() {
        if (hasSelectedProducts()) {
            int size = this.selectedIngredients.size();
            return size > 1 ? size + " products selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedProducts() {
        return this.selectedIngredients != null && !this.selectedIngredients.isEmpty();
    }

    public void deleteSelectedProducts() {
        this.ingredientsInventory.removeAll(this.selectedIngredients);
        this.selectedIngredients = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Todos los ingredientes eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public void openPage() {
        this.accessService = new Service();
        this.accessService.reDirect("/faces/ingredientsMaintenance.xhtml");
    }

    public List<IngredientTO> getIngredientsInventory() {
        return ingredientsInventory;
    }

    public void setIngredientsInventory(List<IngredientTO> ingredientsInventory) {
        this.ingredientsInventory = ingredientsInventory;
    }

    public IngredientTO getSelectedIngredient() {
        return selectedIngredient;
    }

    public void setSelectedIngredient(IngredientTO selectedIngredient) {
        this.selectedIngredient = selectedIngredient;
    }

    public List<IngredientTO> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setSelectedIngredients(List<IngredientTO> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }

    public IngredientService getIngredientService() {
        return ingredientService;
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public Service getAccessService() {
        return accessService;
    }

    public void setAccessService(Service accessService) {
        this.accessService = accessService;
    }

}
